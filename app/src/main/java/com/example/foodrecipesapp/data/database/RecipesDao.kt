package com.example.foodrecipesapp.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipesEntity:RecipesEntity)

    @Delete
    suspend fun delete(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun getRecipes(): Flow<List<RecipesEntity>>


}