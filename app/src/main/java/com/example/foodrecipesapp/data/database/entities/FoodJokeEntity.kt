package com.example.foodrecipesapp.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipesapp.data.dto.JokeResponse


@Entity(tableName = "food_joke_table")
class FoodJokeEntity(
    @Embedded
    var foodJoke: JokeResponse
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}