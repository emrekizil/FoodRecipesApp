package com.example.foodrecipesapp.data.source.local

import com.example.foodrecipesapp.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insert(recipesEntity: RecipesEntity)
    suspend fun delete(recipesEntity: RecipesEntity)
    fun getRecipes(): Flow<List<RecipesEntity>>
}