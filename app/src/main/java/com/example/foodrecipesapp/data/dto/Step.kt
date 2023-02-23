package com.example.foodrecipesapp.data.dto


import com.google.gson.annotations.SerializedName

data class Step(
    @SerializedName("equipment")
    val equipment: List<Equipment>,
    @SerializedName("ingredients")
    val ingredients: List<İngredient>,
    @SerializedName("length")
    val length: Length,
    @SerializedName("number")
    val number: Int,
    @SerializedName("step")
    val step: String
)