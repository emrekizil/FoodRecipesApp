package com.example.foodrecipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipesapp.BuildConfig.API_KEY
import com.example.foodrecipesapp.data.NetworkResult
import com.example.foodrecipesapp.databinding.FragmentRecipesBinding
import com.example.foodrecipesapp.ui.MainViewModel
import com.example.foodrecipesapp.ui.adapters.RecipesAdapter
import com.example.foodrecipesapp.util.observeOnce
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var binding:FragmentRecipesBinding
    private val viewModel by viewModels<MainViewModel>()
    private val recipesViewModel by viewModels<RecipesViewModel> ()
    private val adapter by lazy { RecipesAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        readDatabase()
    }

    private fun readDatabase() {

        viewModel.readRecipes.observeOnce(viewLifecycleOwner){
            if(it.isNotEmpty()){
                Log.d("RecipesFragment","read database called")
                adapter.setData(it[0].foodRecipe)
                hideShimmerEffect()
            }else{
                requestData()
            }
        }
    }

    private fun setupRecyclerView(){
        binding.recipesRv.adapter = adapter
        binding.recipesRv.layoutManager = LinearLayoutManager(activity)
        showShimmerEffect()
    }

    private fun requestData(){
        Log.d("RecipesFragment","request api data called")
        viewModel.getRecipes(recipesViewModel.createQueries())
        viewModel.recipesResponse.observe(viewLifecycleOwner){response->
            when(response){
                is NetworkResult.Success ->{
                    hideShimmerEffect()
                    response.data?.let {
                        adapter.setData(it)
                    }
                }
                is NetworkResult.Error ->{
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.message.toString(),Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }

        }
    }
    private fun loadDataFromCache(){
        viewModel.readRecipes.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                adapter.setData(it[0].foodRecipe)
            }
        }
    }
    private fun showShimmerEffect(){
        binding.recipesRv.showShimmer()
    }
    private fun hideShimmerEffect(){
        binding.recipesRv.hideShimmer()
    }


}