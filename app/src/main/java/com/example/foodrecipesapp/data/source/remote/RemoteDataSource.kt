package com.example.foodrecipesapp.data.source.remote

import com.example.foodrecipesapp.data.dto.FoodResponse
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getFoodRecipesWithQueries(queries:Map<String,String>):Response<FoodResponse>
}