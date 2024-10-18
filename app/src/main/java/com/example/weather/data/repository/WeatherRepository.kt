package com.example.weather.data.repository

import com.example.weather.BuildConfig
import com.example.weather.data.response.ReverseGeoCodeResponse
import com.example.weather.data.response.WeatherResponse
import com.example.weather.network.api.APIState
import com.example.weather.network.api.ResponseError
import com.example.weather.network.api.performNetworkApiCall
import com.example.weather.network.service.APIService

interface IWeatherRepository {
    suspend fun getWeatherData(
        latitude: String,
        longitude: String,
        onLoading: (Boolean) -> Unit,
        onSuccess: (WeatherResponse?) -> Unit,
        onError: (ResponseError?) -> Unit
    )
    suspend fun getCityName(
        latitude: String,
        longitude: String,
        onLoading: (Boolean) -> Unit,
        onSuccess: (List<ReverseGeoCodeResponse>?) -> Unit,
        onError: (ResponseError?) -> Unit
    )
    suspend fun getPlacesList(
        cityName: String,
        onLoading: (Boolean) -> Unit,
        onSuccess: (List<ReverseGeoCodeResponse>?) -> Unit,
        onError: (ResponseError?) -> Unit
    )
}

class WeatherRepository(private val service: APIService) : IWeatherRepository {

    override suspend fun getWeatherData(
        latitude: String,
        longitude: String,
        onLoading: (Boolean) -> Unit,
        onSuccess: (WeatherResponse?) -> Unit,
        onError: (ResponseError?) -> Unit
    ) {
        performNetworkApiCall(networkApiCall = {
            service.getWeatherData(latitude, longitude, BuildConfig.API_KEY)
        }).collect { result ->
            when (result) {
                is APIState.Loading -> {
                    onLoading(result.isLoading)
                }

                is APIState.RenderError -> {
                    onError(result.error)
                }

                is APIState.RenderSuccess -> {
                    onSuccess(result.output)
                }
            }
        }
    }

    override suspend fun getCityName(
        latitude: String,
        longitude: String,
        onLoading: (Boolean) -> Unit,
        onSuccess: (List<ReverseGeoCodeResponse>?) -> Unit,
        onError: (ResponseError?) -> Unit
    ) {
        performNetworkApiCall(networkApiCall = {
            service.getCityName(latitude, longitude, 2, BuildConfig.API_KEY)
        }).collect { result ->
            when (result) {
                is APIState.Loading -> {
                    onLoading(result.isLoading)
                }

                is APIState.RenderError -> {
                    onError(result.error)
                }

                is APIState.RenderSuccess -> {
                    onSuccess(result.output)
                }
            }
        }
    }

    override suspend fun getPlacesList(
        cityName: String,
        onLoading: (Boolean) -> Unit,
        onSuccess: (List<ReverseGeoCodeResponse>?) -> Unit,
        onError: (ResponseError?) -> Unit
    ) {
        performNetworkApiCall(networkApiCall = {
            service.getPlacesList(cityName, 5, BuildConfig.API_KEY)
        }).collect { result ->
            when (result) {
                is APIState.Loading -> {
                    onLoading(result.isLoading)
                }

                is APIState.RenderError -> {
                    onError(result.error)
                }

                is APIState.RenderSuccess -> {
                    onSuccess(result.output)
                }
            }
        }
    }

}