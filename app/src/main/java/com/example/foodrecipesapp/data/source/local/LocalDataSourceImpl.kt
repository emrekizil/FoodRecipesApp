package com.example.foodrecipesapp.data.source.local

import com.example.foodrecipesapp.data.database.RecipesDao
import com.example.foodrecipesapp.data.database.entities.FavoritesEntity
import com.example.foodrecipesapp.data.database.entities.FoodJokeEntity
import com.example.foodrecipesapp.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val recipesDao: RecipesDao
) : LocalDataSource {

    override suspend fun insert(recipesEntity: RecipesEntity){
        recipesDao.insert(recipesEntity)
    }

    override fun getRecipes():Flow<List<RecipesEntity>> = recipesDao.getRecipes()

    override suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    override suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    override suspend fun deleteAllFavoriteRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }

    override fun getFavoriteRecipes(): Flow<List<FavoritesEntity>> =
        recipesDao.getFavoriteRecipes()

    override suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    override fun getFoodJoke(): Flow<List<FoodJokeEntity>> = recipesDao.getFoodJoke()
}