package com.example.foodrecipesapp.data.repository

import com.example.foodrecipesapp.data.source.remote.RemoteDataSource
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : FoodRepository {

    override fun returnRemote(): RemoteDataSource {
        return remoteDataSource
    }
}