package com.example.foodrecipesapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodrecipesapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipesapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodrecipesapp.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val appContext:Context) {

    private object PreferencesKeys{
        val selectedMealType = stringPreferencesKey("mealType")
        val selectedMealTypeId = intPreferencesKey("mealTypeId")
        val selectedDietType = stringPreferencesKey("dietType")
        val selectedDietTypeId = intPreferencesKey("dietTypeId")
        val networkBackOnline = booleanPreferencesKey("backOnline")
    }

    private val dataStore : DataStore<Preferences> = appContext.dataStore

    suspend fun saveMealAndDietType(mealType:String,mealTypeId:Int,dietType: String,dietTypeId:Int){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.selectedMealType] = mealType
            preferences[PreferencesKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferencesKeys.selectedDietType] = dietType
            preferences[PreferencesKeys.selectedDietTypeId] = dietTypeId
        }
    }

    suspend fun saveNetworkStatus(networkStatus:Boolean){
        dataStore.edit { preferences->
            preferences[PreferencesKeys.networkBackOnline] = networkStatus
        }
    }



    val readMealAndDietType : Flow<MealAndDietType> = dataStore.data
        .catch { e->
            if (e is IOException){
                emit(emptyPreferences())
            }else{
                throw e
            }
        }.map { preferences ->
            val selectedMealType = preferences[PreferencesKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferencesKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferencesKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferencesKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,selectedMealTypeId,selectedDietType,selectedDietTypeId
            )
        }

    val readNetworkStatus : Flow<Boolean> = dataStore.data
        .catch { e ->
            if(e is IOException){
                emit(emptyPreferences())
            }else{
                throw  e
            }
        }
        .map { preferences ->
            val networkStatus = preferences[PreferencesKeys.networkBackOnline] ?: false
            networkStatus
        }
}

data class MealAndDietType(
    val selectedMealType:String,
    val selectedMealTypeId:Int,
    val selectedDietType:String,
    val selectedDietTypeId:Int
)