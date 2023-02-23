package com.example.foodrecipesapp.ui.fragments

import android.os.Bundle
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
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestData()
    }

    private fun setupRecyclerView(){
        binding.recipesRv.adapter = adapter
        binding.recipesRv.layoutManager = LinearLayoutManager(activity)
        showShimmerEffect()
    }

    private fun requestData(){
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
                    Toast.makeText(requireContext(), response.message.toString(),Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
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