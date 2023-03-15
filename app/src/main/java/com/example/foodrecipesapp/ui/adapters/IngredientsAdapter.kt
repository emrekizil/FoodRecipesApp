package com.example.foodrecipesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.data.dto.ExtendedIngredient
import com.example.foodrecipesapp.databinding.ItemIngredientsBinding
import com.example.foodrecipesapp.util.Constants.Companion.BASE_IMG_URL
import com.example.foodrecipesapp.util.RecipesDiffUtil

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()



    class IngredientsViewHolder( private val binding: ItemIngredientsBinding):RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup) : IngredientsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemIngredientsBinding.inflate(layoutInflater,parent,false)
                return IngredientsViewHolder(binding)
            }

        }
        fun bind(item:ExtendedIngredient){
            binding.ingredientsIv.load(BASE_IMG_URL+item.image){
                crossfade(600)
                error(R.drawable.ic_placeholder_image)
            }
            binding.ingredientsAmountTv.text = item.amount.toString()
            binding.ingredientUnitTv.text =item.unit
            binding.ingredientOriginalTv.text =item.original
            binding.ingredientsTv.text =item.name.replaceFirstChar {it ->
                it.uppercase()
            }
            binding.consistencyTv.text=item.consistency

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
      return  ingredientsList.size
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val current = ingredientsList[position]
        holder.bind(current)
    }

    fun setData(newIngredients:List<ExtendedIngredient>){
        val ingredientsDiffUtil=RecipesDiffUtil(ingredientsList,newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}