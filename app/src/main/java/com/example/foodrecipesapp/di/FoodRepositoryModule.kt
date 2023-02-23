package com.example.foodrecipesapp.di

import com.example.foodrecipesapp.data.repository.FoodRepository
import com.example.foodrecipesapp.data.repository.FoodRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FoodRepositoryModule {

    @Binds
    abstract fun bindFoodRepository(foodRepositoryImpl: FoodRepositoryImpl):FoodRepository
}