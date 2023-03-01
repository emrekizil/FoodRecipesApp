package com.example.foodrecipesapp.ui.fragments.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.data.dto.Result
import com.example.foodrecipesapp.databinding.FragmentIngredientsBinding
import com.example.foodrecipesapp.ui.adapters.IngredientsAdapter


class IngredientsFragment : Fragment() {

    private lateinit var adapter:IngredientsAdapter

    private lateinit var binding:FragmentIngredientsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIngredientsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val recipeBundle : Result? = args?.getParcelable("recipeBundle")
        setupRv()
        recipeBundle?.extendedIngredients?.let {
            adapter.setData(it)
        }
    }

    private fun setupRv(){
        adapter = IngredientsAdapter()
        binding.ingredientsRv.adapter = adapter
        binding.ingredientsRv.layoutManager = LinearLayoutManager(activity)
    }

}