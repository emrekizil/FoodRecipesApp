package com.example.foodrecipesapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodrecipesapp.data.NetworkResult
import com.example.foodrecipesapp.data.database.RecipesEntity
import com.example.foodrecipesapp.data.dto.FoodResponse

class RecipesBinding {
    companion object{


        @BindingAdapter("getApiResponse","getDatabase", requireAll = true)
        @JvmStatic
        fun setErrorImageViewVisibility(
            imageView: ImageView,
            apiResponse:NetworkResult<FoodResponse>?,
            database:List<RecipesEntity>?
        ){
            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                imageView.visibility = View.VISIBLE
            }else if(apiResponse is NetworkResult.Loading){
                imageView.visibility = View.INVISIBLE
            }else if ( apiResponse is NetworkResult.Success){
                imageView.visibility = View.INVISIBLE
            }

        }

        @BindingAdapter("getApiResponse","getDatabase", requireAll = true)
        @JvmStatic
        fun setErrorTextViewVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodResponse>?,
            database: List<RecipesEntity>?
        ){
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            }else if(apiResponse is NetworkResult.Loading){
                textView.visibility = View.INVISIBLE
            }else if(apiResponse is NetworkResult.Success){
                textView.visibility = View.INVISIBLE
            }
        }
    }
}