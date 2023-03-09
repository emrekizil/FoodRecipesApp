package com.example.foodrecipesapp.data.source.remote

import com.example.foodrecipesapp.data.api.FoodApi
import com.example.foodrecipesapp.data.dto.FoodResponse
import com.example.foodrecipesapp.data.dto.JokeResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api:FoodApi
) : RemoteDataSource{


    override suspend fun getFoodRecipesWithQueries(queries: Map<String, String>): Response<FoodResponse> =
        api.getFoodRecipesWithQueries(queries)

    override suspend fun getFoodRecipesWithSearchQueries(searchQueries: Map<String, String>): Response<FoodResponse> =
        api.getFoodRecipesWithSearchQueries(searchQueries)

    override suspend fun getFoodJoke(): Response<JokeResponse> =
        api.getFoodJoke()

}