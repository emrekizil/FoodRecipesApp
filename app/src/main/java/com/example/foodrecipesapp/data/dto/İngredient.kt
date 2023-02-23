package com.example.foodrecipesapp.data.dto


import com.google.gson.annotations.SerializedName

data class İngredient(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("localizedName")
    val localizedName: String,
    @SerializedName("name")
    val name: String
)