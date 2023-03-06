package com.example.foodrecipesapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.foodrecipesapp.data.NetworkResult
import com.example.foodrecipesapp.data.database.entities.FavoritesEntity
import com.example.foodrecipesapp.data.database.entities.RecipesEntity
import com.example.foodrecipesapp.data.dto.FoodResponse
import com.example.foodrecipesapp.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FoodRepository,
    application: Application
) : AndroidViewModel(application) {


    /*Room*/
    val readRecipes :LiveData<List<RecipesEntity>> = repository.returnLocal().getRecipes().asLiveData()

    val readFavoriteRecipes:LiveData<List<FavoritesEntity>> = repository.returnLocal().getFavoriteRecipes().asLiveData()



    private fun insertRecipes(recipesEntity: RecipesEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.returnLocal().insert(recipesEntity)
        }
    }

    fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.returnLocal().insertFavoriteRecipe(favoritesEntity)
        }
    }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.returnLocal().deleteFavoriteRecipe(favoritesEntity)
        }
    }

     fun deleteAllFavoriteRecipes(){
        viewModelScope.launch(Dispatchers.IO){
            repository.returnLocal().deleteAllFavoriteRecipes()
        }
    }


    private var _recipesResponse :MutableLiveData<NetworkResult<FoodResponse>> = MutableLiveData()
    val recipesResponse : LiveData<NetworkResult<FoodResponse>> get() = _recipesResponse

    private var _searchedRecipesResponse : MutableLiveData<NetworkResult<FoodResponse>> = MutableLiveData()
    val searchedRecipesResponse : LiveData<NetworkResult<FoodResponse>> get() = _searchedRecipesResponse

    fun getRecipes(queries:Map<String,String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun getSearchedRecipes(searchQueries:Map<String,String>) = viewModelScope.launch {
        getSearchedRecipesSafeCall(searchQueries)
    }



    private suspend fun getRecipesSafeCall(queries: Map<String, String>){
        if(hasInternetConnection()){
            try {
                val response = repository.returnRemote().getFoodRecipesWithQueries(queries)
                _recipesResponse.value = handleFoodRecipesResponse(response)

                val foodRecipes = _recipesResponse.value?.data
                if (foodRecipes !=null){
                    offlineCacheRecipes(foodRecipes)
                }
            }catch (e:Exception){
                _recipesResponse.value = NetworkResult.Error("Recipes Not Found")
            }
        }else{
            _recipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private suspend fun getSearchedRecipesSafeCall(searchQueries: Map<String, String>) {
        if (hasInternetConnection()){
            try {
                val response = repository.returnRemote().getFoodRecipesWithSearchQueries(searchQueries)
                _searchedRecipesResponse.value = handleFoodRecipesResponse(response)
            }catch (e:Exception){
                _searchedRecipesResponse.value = NetworkResult.Error("Recipes Not Found")
            }
        }else{
            _searchedRecipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheRecipes(foodRecipes: FoodResponse) {
        val recipesEntity = RecipesEntity(foodRecipes)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodResponse>): NetworkResult<FoodResponse>? {
        when{
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection():Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?:return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}