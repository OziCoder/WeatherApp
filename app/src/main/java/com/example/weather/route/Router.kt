package com.example.weather.route

import androidx.navigation.NavController

fun NavController.executeNavigationAction(action: NavigationAction){
    when(action){
        is NavigationAction.NavigateTo -> {
            navigate(action.route)
        }
        is NavigationAction.NavigateBack -> {
            popBackStack()
        }
        is NavigationAction.PopAllAndNavigateTo ->{
            navigate(action.route){
                popUpTo(graph.id){
                    inclusive = true
                }
            }
        }
        is NavigationAction.None -> Unit
    }
}