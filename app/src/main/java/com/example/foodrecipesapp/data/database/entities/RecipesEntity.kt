package com.example.foodrecipesapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipesapp.data.dto.FoodResponse

@Entity(tableName = "recipes_table")
class RecipesEntity(
    var foodRecipe : FoodResponse
){
    @PrimaryKey(autoGenerate = false)
    var id :Int =0
}

