package com.example.celfocus.domain.usecases

import com.example.celfocus.domain.repositories.WeatherRepositoryImpl

class GetWeatherUseCase(private val weatherRepository: WeatherRepositoryImpl) {
    suspend operator fun invoke(city: String, appKey: String, units: String)
        = weatherRepository.getWeatherForecast(city, appKey, units)
}
