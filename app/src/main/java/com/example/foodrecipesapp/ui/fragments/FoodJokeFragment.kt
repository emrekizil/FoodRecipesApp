package com.example.foodrecipesapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.data.NetworkResult
import com.example.foodrecipesapp.databinding.FragmentFoodJokeBinding
import com.example.foodrecipesapp.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var _binding : FragmentFoodJokeBinding

    private lateinit var menuHost: MenuHost

    private var foodJoke = "No Food Joke"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentFoodJokeBinding.inflate(layoutInflater)
        _binding.lifecycleOwner = viewLifecycleOwner
        _binding.viewModel = viewModel
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFoodJoke()
        viewModel.foodJokeResponse.observe(viewLifecycleOwner){response->
            when(response){
                is NetworkResult.Success->{
                    _binding.jokeTv.text = response.data?.text
                    foodJoke = response.data?.text.toString()
                }
                is NetworkResult.Error->{
                    loadDataFromCache()
                    Toast.makeText(requireContext(),response.message,Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading->{
                    Log.d("FoodJokeFragment","HEY")
                }
            }

        }

        menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.share_joke_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId== R.id.shareJokeMenu){
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT,foodJoke)
                        type ="text/plain"

                    }
                    startActivity(shareIntent)
                }
                return true
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            viewModel.readFoodJoke.observe(viewLifecycleOwner){
                if(!it.isNullOrEmpty()){
                    _binding.jokeTv.text = it[0].foodJoke.text
                    foodJoke = it[0].foodJoke.text
                }
            }
        }
    }





}