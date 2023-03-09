package com.example.foodrecipesapp.data.dto


import com.google.gson.annotations.SerializedName

data class JokeResponse(
    @SerializedName("text")
    val text: String
)