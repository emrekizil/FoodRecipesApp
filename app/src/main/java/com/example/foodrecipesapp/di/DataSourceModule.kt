package com.example.foodrecipesapp.di

import com.example.foodrecipesapp.data.source.local.LocalDataSource
import com.example.foodrecipesapp.data.source.local.LocalDataSourceImpl
import com.example.foodrecipesapp.data.source.remote.RemoteDataSource
import com.example.foodrecipesapp.data.source.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRemoteDataSource( remoteDataSourceImpl: RemoteDataSourceImpl):RemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindLocalDataSource( localDataSourceImpl: LocalDataSourceImpl) : LocalDataSource
}