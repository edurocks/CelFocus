package com.example.celfocus.network

import com.example.celfocus.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {

    @GET("forecast")
    suspend fun getWeatherForecast(@Query("q") city : String, @Query("appid")  key : String,
                                   @Query("units") units : String)
                                    : Response<WeatherResponse>
}