package com.example.weather.network.api

import com.example.weather.network.service.APIService
import okhttp3.OkHttpClient
import retrofit2.Retrofit

//For DI purposes
fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return APIFactory.retrofit(okHttpClient)
}

fun provideOkHttpClient(): OkHttpClient {
    return APIFactory.getHttpClient()
}

fun provideApiService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)
