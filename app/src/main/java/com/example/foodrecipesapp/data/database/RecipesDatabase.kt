package com.example.foodrecipesapp.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipesEntity::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class RecipesDatabase : RoomDatabase(){
    abstract fun recipesDao(): RecipesDao
}