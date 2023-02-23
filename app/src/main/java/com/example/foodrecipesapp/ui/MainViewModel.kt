package com.example.foodrecipesapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipesapp.data.NetworkResult
import com.example.foodrecipesapp.data.dto.FoodResponse
import com.example.foodrecipesapp.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FoodRepository,
    application: Application
) : AndroidViewModel(application) {

    private var _recipesResponse :MutableLiveData<NetworkResult<FoodResponse>> = MutableLiveData()
     val recipesResponse : LiveData<NetworkResult<FoodResponse>> get() = _recipesResponse

    fun getRecipes(queries:Map<String,String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }
    private suspend fun getRecipesSafeCall(queries: Map<String, String>){
        if(hasInternetConnection()){
            try {
                val response = repository.returnRemote().getFoodRecipesWithQueries(queries)
                _recipesResponse.value = handleFoodRecipesResponse(response)
            }catch (e:Exception){

            }
        }else{
            _recipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
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