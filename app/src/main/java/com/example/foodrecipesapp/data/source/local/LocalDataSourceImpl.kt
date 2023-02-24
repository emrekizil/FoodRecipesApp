package com.example.foodrecipesapp.data.source.local

import com.example.foodrecipesapp.data.database.RecipesDao
import com.example.foodrecipesapp.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val recipesDao: RecipesDao
) : LocalDataSource {

    override suspend fun insert(recipesEntity: RecipesEntity){
        recipesDao.insert(recipesEntity)
    }
    override suspend fun delete(recipesEntity: RecipesEntity){
        recipesDao.delete(recipesEntity)
    }

    override fun getRecipes():Flow<List<RecipesEntity>>{
        return recipesDao.getRecipes()
    }
}