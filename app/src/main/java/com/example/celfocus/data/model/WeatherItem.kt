package com.example.celfocus.data.model

import com.example.celfocus.domain.entities.WeatherEntity

data class WeatherItem(var date: String = "", var weatherList: ArrayList<WeatherEntity> = arrayListOf())

