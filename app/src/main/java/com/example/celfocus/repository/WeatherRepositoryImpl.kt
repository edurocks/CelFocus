package com.example.celfocus.repository

import com.example.celfocus.model.Weather

interface WeatherRepositoryImpl {
    suspend fun getWeatherForecast(city : String, appKey : String, units : String)
                                   :  Resource<List<Weather>>?
}