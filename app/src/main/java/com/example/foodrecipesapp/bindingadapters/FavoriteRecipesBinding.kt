package com.example.foodrecipesapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipesapp.data.database.entities.FavoritesEntity
import com.example.foodrecipesapp.ui.adapters.FavoriteRecipesAdapter

class FavoriteRecipesBinding {

    companion object {
        /*@BindingAdapter("viewVisibility","setData", requireAll = false)
        @JvmStatic
        fun setViewVisibility(
            view: View,
            favoritesEntity: List<FavoritesEntity>?,
            adapter: FavoriteRecipesAdapter?
        ) {
            if(favoritesEntity.isNullOrEmpty()){
                when(view){
                    is ImageView->{
                        view.visibility = View.VISIBLE
                    }
                    is TextView ->{
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView->{
                        view.visibility = View.INVISIBLE
                    }
                }
            }else{
                when(view){
                    is ImageView->{
                        view.visibility = View.INVISIBLE
                    }
                    is TextView ->{
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView->{
                        view.visibility = View.VISIBLE
                        adapter?.setData(favoritesEntity)
                    }
                }
            }

        }*/

        @BindingAdapter("viewVisibility","setData", requireAll = false)
        @JvmStatic
        fun setVisibility(view: View,favoritesEntity:List<FavoritesEntity>?,adapter: FavoriteRecipesAdapter?){
            when(view){
                is RecyclerView ->{
                    val dataCheck = favoritesEntity.isNullOrEmpty()
                    view.isInvisible =  dataCheck
                    if(!dataCheck){
                        favoritesEntity?.let {
                            adapter?.setData(it)
                        }
                    }
                }
                else -> view.isVisible = favoritesEntity.isNullOrEmpty()
            }
        }
    }




}