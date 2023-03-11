package com.example.foodrecipesapp.data.database

import androidx.room.*
import com.example.foodrecipesapp.data.database.entities.FavoritesEntity
import com.example.foodrecipesapp.data.database.entities.FoodJokeEntity
import com.example.foodrecipesapp.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipesDao {
    //Recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun getRecipes(): Flow<List<RecipesEntity>>

    //Favorite Recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Delete
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun getFavoriteRecipes():Flow<List<FavoritesEntity>>

    //Food Joke
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)
    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun getFoodJoke():Flow<List<FoodJokeEntity>>







}