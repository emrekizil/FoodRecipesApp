package com.example.foodrecipesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipesapp.data.dto.FoodResponse
import com.example.foodrecipesapp.databinding.ItemRecipesBinding
import com.example.foodrecipesapp.data.dto.Result
import com.example.foodrecipesapp.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {
    private var recipes = emptyList<Result>()

    class RecipesViewHolder(private val binding: ItemRecipesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result){
                binding.result = result
                binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup) : RecipesViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecipesBinding.inflate(layoutInflater,parent,false)
                return RecipesViewHolder(binding)
            }

        }
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
    fun setData(newData:FoodResponse){
        val recipesDiffUtil = RecipesDiffUtil(recipes,newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}