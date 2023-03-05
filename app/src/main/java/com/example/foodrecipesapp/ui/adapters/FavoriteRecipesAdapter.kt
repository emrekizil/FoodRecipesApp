package com.example.foodrecipesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipesapp.data.database.entities.FavoritesEntity
import com.example.foodrecipesapp.databinding.ItemFavoriteRecipesBinding
import com.example.foodrecipesapp.util.RecipesDiffUtil

class FavoriteRecipesAdapter:RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteRecipesViewHolder>() {

    private var favoriteRecipes = emptyList<FavoritesEntity>()
    class FavoriteRecipesViewHolder(private val binding:ItemFavoriteRecipesBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(favoritesEntity: FavoritesEntity){
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):FavoriteRecipesViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFavoriteRecipesBinding.inflate(layoutInflater,parent,false)
                return FavoriteRecipesViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipesViewHolder {
        return FavoriteRecipesViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    override fun onBindViewHolder(holder: FavoriteRecipesViewHolder, position: Int) {
        val item = favoriteRecipes[position]
        holder.bind(item)
    }

    fun setData(newData:List<FavoritesEntity>){
        val diffUtil = RecipesDiffUtil(favoriteRecipes,newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        favoriteRecipes = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}