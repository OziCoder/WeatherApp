package com.example.weather.network.api

/** To represent different UI states**/
sealed class APIState<out T : Any> {
    data class Loading(val isLoading: Boolean) : APIState<Nothing>()
    data class RenderSuccess<out T : Any>(val output: T?, val code: Int) : APIState<T>()
    data class RenderError(val error: ResponseError? = null) : APIState<Nothing>()
}
