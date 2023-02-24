package com.example.foodrecipesapp.di


import android.content.Context
import androidx.room.Room
import com.example.foodrecipesapp.data.database.RecipesDao
import com.example.foodrecipesapp.data.database.RecipesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideRecipesDatabase(@ApplicationContext appContext : Context):RecipesDatabase{
        return Room.databaseBuilder(
            appContext,
            RecipesDatabase::class.java,
            "recipes_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipesDao(recipesDatabase: RecipesDatabase) : RecipesDao =
         recipesDatabase.recipesDao()

}