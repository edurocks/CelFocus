package com.example.celfocus.data.repository

import com.example.celfocus.data.mapper.WeatherMapper
import com.example.celfocus.domain.entities.WeatherEntity
import com.example.celfocus.domain.repositories.WeatherRepositoryImpl
import com.example.celfocus.data.network.WeatherInterface
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class WeatherRepository @Inject constructor(private val weatherRepository: WeatherInterface,
                                            private val weatherMapper: WeatherMapper
)
    : WeatherRepositoryImpl {

    override suspend fun getWeatherForecast(city: String, appKey: String, units: String)
                                                           : Resource<List<WeatherEntity>>? {

        val response = weatherRepository.getWeatherForecast(city, appKey, units)
        var result : Resource<List<WeatherEntity>>? = null

        if (response.isSuccessful && response.code() == HttpsURLConnection.HTTP_OK){
            response.body()?.let {
                result = Resource.success(weatherMapper.weatherToWeatherEntity(it))
            }
        }else{
            result = Resource.error(null, response.message())
        }

        return result
    }

}