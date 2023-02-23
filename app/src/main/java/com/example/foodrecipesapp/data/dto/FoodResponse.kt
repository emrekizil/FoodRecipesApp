package com.example.foodrecipesapp.data.dto


import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("number")
    val number: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("totalResults")
    val totalResults: Int
)