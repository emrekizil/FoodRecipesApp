package com.example.foodrecipesapp.data.repository

import com.example.foodrecipesapp.data.source.local.LocalDataSource
import com.example.foodrecipesapp.data.source.remote.RemoteDataSource
import java.security.PrivateKey
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : FoodRepository {

    override fun returnRemote(): RemoteDataSource {
        return remoteDataSource
    }

    override fun returnLocal():LocalDataSource{
        return localDataSource
    }
}