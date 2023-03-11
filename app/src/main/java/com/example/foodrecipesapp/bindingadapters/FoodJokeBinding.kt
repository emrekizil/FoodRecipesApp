package com.example.foodrecipesapp.bindingadapters

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodrecipesapp.data.NetworkResult
import com.example.foodrecipesapp.data.database.entities.FoodJokeEntity
import com.example.foodrecipesapp.data.dto.FoodResponse
import com.example.foodrecipesapp.data.dto.JokeResponse
import com.google.android.material.card.MaterialCardView

class FoodJokeBinding {
    companion object{

        @BindingAdapter("apiResponse3","readDatabase3", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,
            apiRepsonse:NetworkResult<JokeResponse>,
            database:List<FoodJokeEntity>?
        ){

            when(apiRepsonse){
                is NetworkResult.Loading->{
                    when(view){
                        is ProgressBar->{
                            view.visibility = View.VISIBLE
                        }
                        is MaterialCardView->{
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Error->{
                    when(view){
                        is ProgressBar->{
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView->{
                            view.visibility = View.VISIBLE
                            if(database!=null){
                                if(database.isEmpty()){
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Success->{
                    when(view){
                        is ProgressBar->{
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView->{
                            view.visibility = View.VISIBLE
                        }
                    }
                }
            }


        }

        @BindingAdapter("apiResponse4","readDatabase4", requireAll = true)
        @JvmStatic
        fun setErrorViewsVisibility(
            view: View,
            apiResponse: NetworkResult<JokeResponse>?,
            database: List<FoodJokeEntity>?
        ){
            if(database.isNullOrEmpty()){
                view.visibility=View.VISIBLE
                if (view is TextView){
                    if(apiResponse!=null){
                        view.text = apiResponse.message.toString()
                    }
                }
            }
            if(apiResponse is NetworkResult.Success){
                view.visibility = View.INVISIBLE
            }

        }
    }
}