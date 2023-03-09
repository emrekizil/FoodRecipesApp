package com.example.foodrecipesapp.ui.fragments

import android.os.Binder
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter :FavoriteRecipesAdapter
    private lateinit var menuHost: MenuHost

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
        menuHost = requireActivity()
        menuHost.addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.favorite_recipes_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId==R.id.deleteAllFavoriteRecipesMenu){
                    viewModel.deleteAllFavoriteRecipes()
                    showSnackbar("All recipes removed")
                }
                return true
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)
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