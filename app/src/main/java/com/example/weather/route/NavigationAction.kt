package com.example.weather.route

sealed class NavigationAction {
    object None : NavigationAction()
    data class NavigateTo(val route : String) : NavigationAction()
    data class PopAllAndNavigateTo(val route : String) : NavigationAction()
    object NavigateBack : NavigationAction()
}