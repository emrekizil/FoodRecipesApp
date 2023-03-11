package com.example.foodrecipesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodrecipesapp.data.database.entities.FavoritesEntity
import com.example.foodrecipesapp.data.database.entities.FoodJokeEntity
import com.example.foodrecipesapp.data.database.entities.RecipesEntity

@Database(entities = [RecipesEntity::class,FavoritesEntity::class,FoodJokeEntity::class], version = 3, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class RecipesDatabase : RoomDatabase(){
    abstract fun recipesDao(): RecipesDao
}