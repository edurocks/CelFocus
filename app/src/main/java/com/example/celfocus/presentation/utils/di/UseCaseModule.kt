package com.example.celfocus.presentation.utils.di

import com.example.celfocus.domain.repositories.WeatherRepositoryImpl
import com.example.celfocus.domain.usecases.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun providesWeatherUseCases(weatherRepository: WeatherRepositoryImpl) : GetWeatherUseCase{
        return GetWeatherUseCase(weatherRepository)
    }
}