package com.example.weather.ui.search

import androidx.lifecycle.viewModelScope
import com.example.weather.base.BaseViewModel
import com.example.weather.base.ClickAction
import com.example.weather.base.PreferencesManager
import com.example.weather.base.UIAction
import com.example.weather.data.response.ReverseGeoCodeResponse
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.route.NavigationAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val weatherRepository: WeatherRepository,
    private val preferencesManager: PreferencesManager
) : BaseViewModel() {
    private var _placesList = MutableStateFlow(emptyList<ReverseGeoCodeResponse>())
    val placesList: StateFlow<List<ReverseGeoCodeResponse>> get() = _placesList

    private var _siteSearchText: String? = ""
    var siteSearchText: String?
        get() {
            return _siteSearchText
        }
        set(searchString) {
            _siteSearchText = searchString
            _siteSearchText?.let {
                if (it.length >= 2) {
                    getPlacesList(it)
                } else {
                    clearPlaceList()
                }
            }
        }

    override fun handleAction(uiAction: UIAction) {
        when (uiAction) {
            is SearchScreenActions.GoToLandingScreen -> {
                performNavigationAction(NavigationAction.NavigateBack)
            }
        }
    }

    private fun getPlacesList(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getPlacesList(
                "$cityName,US",
                onLoading = {

                },
                onSuccess = { response ->
                    if (!response.isNullOrEmpty()) {
                        _placesList.value = response
                    }
                },
                onError = { error ->
                    _placesList.value = emptyList<ReverseGeoCodeResponse>()
                    throw java.lang.Exception(error?.message.toString())
                })

        }
    }

    /** To remove search list when entry is removed from search text field **/
    private fun clearPlaceList() {
        _placesList.value = emptyList()
    }
    /** To save and access the last searched location **/
    fun saveSelectedPlacesWeather(selectedPlace: ReverseGeoCodeResponse) {
        preferencesManager.savedLocation = selectedPlace
    }
}

sealed class SearchScreenActions : ClickAction() {
    data object GoToLandingScreen : SearchScreenActions()
}