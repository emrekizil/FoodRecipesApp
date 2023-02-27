package com.example.foodrecipesapp.data.api

import com.example.foodrecipesapp.BuildConfig.API_KEY
import com.example.foodrecipesapp.data.dto.FoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodApi {

    @GET("/recipes/complexSearch")
    suspend fun getFoodRecipesWithQueries(
        @QueryMap queries: Map<String,String>
    ) : Response<FoodResponse>

    @GET("/recipes/complexSearch")
    suspend fun getFoodRecipesWithSearchQueries(
        @QueryMap searchQueries: Map<String,String>
    ) : Response<FoodResponse>

}