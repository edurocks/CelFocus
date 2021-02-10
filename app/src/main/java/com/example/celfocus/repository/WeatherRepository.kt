package com.example.celfocus.repository

import com.example.celfocus.model.Weather
import com.example.celfocus.network.WeatherInterface
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class WeatherRepository @Inject constructor(private val weatherRepository: WeatherInterface)
    : WeatherRepositoryImpl{

    override suspend fun getWeatherForecast(city: String, appKey: String, units: String)
                                                           :  Resource<List<Weather>>? {

        val response = weatherRepository.getWeatherForecast(city, appKey, units)
        var result : Resource<List<Weather>>? = null

        if (response.isSuccessful && response.code() == HttpsURLConnection.HTTP_OK){
            response.body()?.let {
                result = Resource.success(it.list)
            }
        }else{
            result = Resource.error(null, response.message())
        }

        return result
    }

}