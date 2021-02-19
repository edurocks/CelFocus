package com.example.celfocus.presentation.utils.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}