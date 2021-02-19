package com.example.celfocus.data.mapper

import com.example.celfocus.domain.entities.MainEntity
import com.example.celfocus.domain.entities.WeatherEntity
import com.example.celfocus.domain.entities.WeatherInfoEntity
import com.example.celfocus.data.model.Main
import com.example.celfocus.data.model.WeatherInfo
import com.example.celfocus.data.model.WeatherResponse

class WeatherMapper {
    fun weatherToWeatherEntity(response : WeatherResponse) : List<WeatherEntity>{
        return response.list.map {
            WeatherEntity(
                mapMainToMainEntity(it.main),
                mapWeatherInfoToWeatherInfoEntity(it.weather),
                it.dateTime
            )
        }
    }

    private fun mapMainToMainEntity(main: Main): MainEntity {
        return MainEntity(main.temp)
    }

    private fun mapWeatherInfoToWeatherInfoEntity(weatherInfoList: List<WeatherInfo>)
                                                            : List<WeatherInfoEntity>{
        return weatherInfoList.map { mapToWeatherEntity(it) }
    }

    private fun mapToWeatherEntity(it: WeatherInfo) : WeatherInfoEntity {
        return WeatherInfoEntity(
            it.main,
            it.icon
        )
    }
}