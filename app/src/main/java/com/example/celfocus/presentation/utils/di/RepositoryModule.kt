package com.example.celfocus.presentation.utils.di

import com.example.celfocus.data.mapper.WeatherMapper
import com.example.celfocus.data.network.WeatherInterface
import com.example.celfocus.data.repository.WeatherRepository
import com.example.celfocus.domain.repositories.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesWeatherRepository(weatherInterface: WeatherInterface, weatherMapper: WeatherMapper) :
            WeatherRepositoryImpl{
        return WeatherRepository(weatherInterface, weatherMapper)
    }
}