package com.example.foodrecipesapp.ui.fragments

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.databinding.FragmentFavoriteRecipesBinding
import com.example.foodrecipesapp.ui.MainViewModel
import com.example.foodrecipesapp.ui.adapters.FavoriteRecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private lateinit var binding:FragmentFavoriteRecipesBinding

    private val adapter = FavoriteRecipesAdapter()

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteRecipesBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
       // observeDatabase()
    }

    private fun observeDatabase() {
        viewModel.readFavoriteRecipes.observe(viewLifecycleOwner){
            adapter.setData(it)
        }
    }

    private fun setupRecyclerView(){
        binding.adapter = adapter
        binding.favoriteRecipesRv.adapter = adapter
        binding.favoriteRecipesRv.layoutManager = LinearLayoutManager(requireContext())
    }


}