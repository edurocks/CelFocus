package com.example.celfocus.presentation.utils.di

import com.example.celfocus.data.mapper.WeatherMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun providesWeatherMapper() : WeatherMapper {
        return WeatherMapper()
    }
}