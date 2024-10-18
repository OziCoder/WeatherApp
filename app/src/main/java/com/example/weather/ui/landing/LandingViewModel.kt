package com.example.weather.ui.landing

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.example.weather.base.BaseViewModel
import com.example.weather.base.ClickAction
import com.example.weather.base.UIAction
import com.example.weather.constants.ScreenConstants
import com.example.weather.data.response.ReverseGeoCodeResponse
import com.example.weather.data.response.WeatherResponse
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.route.NavigationAction
import com.example.weather.ui.search.SearchScreenRoute
import com.example.weather.utils.LocationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LandingViewModel(private val weatherRepository: WeatherRepository) : BaseViewModel() {
    private var _initialScreen = MutableStateFlow("")
    val initialScreen: MutableStateFlow<String> get() = _initialScreen
    private val _weatherData = MutableStateFlow(WeatherResponse())
    val weatherData: StateFlow<WeatherResponse> = _weatherData
    private val _cityData = MutableStateFlow(ReverseGeoCodeResponse())
    val cityData: StateFlow<ReverseGeoCodeResponse> = _cityData

    override fun handleAction(uiAction: UIAction) {
        when (uiAction) {
            is LandingScreenActions.GoToSearchScreen -> {
                performNavigationAction(NavigationAction.NavigateTo(SearchScreenRoute().path))
            }
        }
    }

    fun getWeatherData(latitude: String, longitude: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getWeatherData(latitude, longitude,
                onLoading = {

                },
                onSuccess = { response ->
                    _weatherData.value = response!!
                },
                onError = { error ->
                    _weatherData.value = WeatherResponse()
                    throw java.lang.Exception(error?.message.toString())
                })

        }
    }

    private fun getCityName(latitude: String, longitude: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getCityName(latitude, longitude,
                onLoading = {

                },
                onSuccess = { response ->
                    if (!response.isNullOrEmpty()) {
                        _cityData.value = response[0]
                    }
                },
                onError = { error ->
                    _cityData.value = ReverseGeoCodeResponse()
                    throw java.lang.Exception(error?.message.toString())
                })

        }
    }

    fun gotoInitialScreen() {
        initialScreen.value = ScreenConstants.LANDING_SCREEN
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            LocationUtils.requestLocationResultCallback(fusedLocationProviderClient) { locationResult ->
                locationResult.lastLocation?.let { location ->
                    getCityName("36.778259", "-119.417931")
                    getWeatherData("36.778259", "-119.417931")
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }

    fun getSelectedWeather(latitude: String, longitude: String){
        getCityName(latitude, longitude)
        getWeatherData(latitude, longitude)
    }
}

sealed class LandingScreenActions : ClickAction() {
    data object GoToSearchScreen : LandingScreenActions()
}