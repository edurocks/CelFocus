package com.example.celfocus.di

import com.example.celfocus.network.WeatherInterface
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    private const val TIMEOUT = 30

    @Provides
    fun provideBaseUrl() = "http://api.openweathermap.org/data/2.5/"

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT.toLong(), java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT.toLong(), java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit{

        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()


    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(WeatherInterface::class.java)
}