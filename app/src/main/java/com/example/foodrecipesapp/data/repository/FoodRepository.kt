package com.example.foodrecipesapp.data.repository

import com.example.foodrecipesapp.data.source.remote.RemoteDataSource

interface FoodRepository {
    fun returnRemote():RemoteDataSource
}