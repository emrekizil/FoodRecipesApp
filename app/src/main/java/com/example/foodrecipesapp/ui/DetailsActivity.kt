package com.example.foodrecipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.data.database.entities.FavoritesEntity
import com.example.foodrecipesapp.databinding.ActivityDetailsBinding
import com.example.foodrecipesapp.ui.adapters.PagerAdapter
import com.example.foodrecipesapp.ui.fragments.detail.IngredientsFragment
import com.example.foodrecipesapp.ui.fragments.detail.InstructionsFragment
import com.example.foodrecipesapp.ui.fragments.detail.OverviewFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()

    private val viewModel by viewModels<MainViewModel>()

    private var recipeSaved = false
    private var savedRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        binding.toolBar.setTitleTextColor(ContextCompat.getColor(this,R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")


        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle",args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPager2.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager2){tab,position ->
            tab.text = titles[position]
        }.attach()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        val menuItem = menu?.findItem(R.id.saveToFavoritesMenu)
        checkSavedRecipes(menuItem)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem?) {
        viewModel.readFavoriteRecipes.observe(this){favoritesEntity->
            try {
                for(savedEntity in favoritesEntity){
                    if(savedEntity.result.id == args.result.id){
                        if (menuItem != null) {
                            changeMenuItemColor(menuItem,R.color.yellow)
                            savedRecipeId = savedEntity.id
                            recipeSaved = true
                        }
                    }else{
                        if (menuItem != null) {
                            changeMenuItemColor(menuItem,R.color.white)
                        }
                    }
                }
            }catch (e:Exception){
                Log.d("DetailsActivity",e.message.toString())
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }else if(item.itemId == R.id.saveToFavoritesMenu&& !recipeSaved){
            saveToFavorites(item)
        }else if(item.itemId ==R.id.saveToFavoritesMenu && recipeSaved){
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)

    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(0,args.result)
        viewModel.insertFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item,R.color.yellow)
        showSnackBar("Recipes saved to your favorites")
        recipeSaved = true

    }

    private fun removeFromFavorites(item: MenuItem){
        val favoritesEntity =FavoritesEntity(
            savedRecipeId,args.result
        )
        viewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item,R.color.white)
        showSnackBar("Removed from favorites")
        recipeSaved = false
    }

    private fun showSnackBar(s: String) {
        Snackbar.make(binding.detailsLayout,s,Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this,color))
    }


}