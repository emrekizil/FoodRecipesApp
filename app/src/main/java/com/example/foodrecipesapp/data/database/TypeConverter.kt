package com.example.foodrecipesapp.data.database


import com.example.foodrecipesapp.data.dto.FoodResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.foodrecipesapp.data.dto.Result

class TypeConverter {
    var gson = Gson()

    @androidx.room.TypeConverter
    fun toJson(foodResponse: FoodResponse):String{
        return gson.toJson(foodResponse)
    }

    @androidx.room.TypeConverter
    fun fromJson(data:String):FoodResponse{
        val listType = object : TypeToken<FoodResponse>(){}.type
        return gson.fromJson(data,listType)
    }

    @androidx.room.TypeConverter
    fun toJsonResult(result: Result):String{
        return gson.toJson(result)
    }

    @androidx.room.TypeConverter
    fun fromJsonResult(data: String):Result{
        val listType = object :TypeToken<Result>(){}.type
        return gson.fromJson(data,listType)
    }
}