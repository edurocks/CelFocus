package com.example.celfocus.domain.repositories

import com.example.celfocus.domain.entities.WeatherEntity
import com.example.celfocus.data.model.Weather
import com.example.celfocus.data.repository.Resource

interface WeatherRepositoryImpl {
    suspend fun getWeatherForecast(city : String, appKey : String, units : String)
                                   :  Resource<List<WeatherEntity>>?
}