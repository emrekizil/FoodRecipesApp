package com.example.foodrecipesapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipesapp.data.dto.Result

@Entity(tableName = "favorite_recipes_table")
data class FavoritesEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var result : Result
)
