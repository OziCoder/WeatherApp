package com.example.weather.network.service

import com.example.weather.BuildConfig
import com.example.weather.data.response.ReverseGeoCodeResponse
import com.example.weather.data.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("${BuildConfig.BASE_URL}/weather")
    suspend fun getWeatherData(
        @Query("lat") latitude: String, @Query("lon") longitude: String,
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>

    @GET("${BuildConfig.GEOCODE_URL}/reverse")
    suspend fun getCityName(
        @Query("lat") latitude: String, @Query("lon") longitude: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): Response<List<ReverseGeoCodeResponse>>

    @GET("${BuildConfig.GEOCODE_URL}/direct")
    suspend fun getPlacesList(
        @Query("q") cityName: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): Response<List<ReverseGeoCodeResponse>>
}