package com.example.foodrecipesapp.data.source.local

import com.example.foodrecipesapp.data.database.entities.FavoritesEntity
import com.example.foodrecipesapp.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insert(recipesEntity: RecipesEntity)
    fun getRecipes(): Flow<List<RecipesEntity>>

    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)
    suspend fun deleteAllFavoriteRecipes()

    fun getFavoriteRecipes():Flow<List<FavoritesEntity>>


}