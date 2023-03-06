package com.example.foodrecipesapp.ui.fragments

import android.os.Binder
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.databinding.FragmentFavoriteRecipesBinding
import com.example.foodrecipesapp.ui.MainViewModel
import com.example.foodrecipesapp.ui.adapters.FavoriteRecipesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private lateinit var binding:FragmentFavoriteRecipesBinding

    //private val adapter = FavoriteRecipesAdapter(requireActivity())
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter :FavoriteRecipesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteRecipesBinding.inflate(layoutInflater)
        adapter = FavoriteRecipesAdapter(requireActivity(),viewModel)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setHasOptionsMenu(true)
       // observeDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.deleteAllFavoriteRecipesMenu){
            viewModel.deleteAllFavoriteRecipes()
            showSnackbar("All recipes removed")
        }
        return super.onOptionsItemSelected(item)
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

    private fun showSnackbar(message:String){
        Snackbar.make(binding.root,message,Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.clearContextualActionMode()
    }


}