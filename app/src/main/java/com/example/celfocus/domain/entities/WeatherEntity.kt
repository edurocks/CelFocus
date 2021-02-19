package com.example.celfocus.domain.entities

data class WeatherEntity(
    val main: MainEntity,
    val weather: List<WeatherInfoEntity>,
    val dateTime: String
)
