package com.example.celfocus.data.model

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<WeatherInfo>,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("rain")
    val rain: Rain,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("dt_txt")
    val dateTime: String
)
