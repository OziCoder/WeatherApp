package com.example.weather.network.api

import com.example.weather.network.NetworkUtils
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.*

const val NO_NETWORK_ERROR = "Please check your network connection and retry"
/**
  Readable naming convention for Network call lambda
 **/
typealias NetworkAPIInvoke<T> = suspend () -> Response<T>

/**
 Utility function that works to perform a Retrofit API call and return either a
 success model
 @param networkApiCall lambda representing a suspend function for the retrofit API call
 @return [T] is the success object
 **/
suspend fun <T : Any> performNetworkApiCall(
    networkApiCall: NetworkAPIInvoke<T>
): Flow<APIState<T>> {
    return flow {
        if (!NetworkUtils.isNetworkConnected()) {
            emit(
                APIState.RenderError(ResponseError(NO_NETWORK_ERROR, ErrorType.NO_NETWORK_ERROR))
            )
            return@flow
        }

        emit(APIState.Loading(true))
        val response = networkApiCall()
        if (response.isSuccessful) {
            emit(APIState.RenderSuccess(response.body(), response.code()))
            emit(APIState.Loading(false))
            return@flow
        } else {
            emit(
                APIState.RenderError(
                    ResponseError(
                        response.code(),
                        Date().time,
                        response.errorBody(),
                        null
                    )
                )
            )
        }
        emit(APIState.Loading(false))
        return@flow
    }.catch { throwable ->
        when (throwable) {
            is IOException -> {
                val errorResponse = createIOErrorBody(throwable)
                APIState.RenderError(errorResponse)
            }

            is HttpException -> {
                val errorResponse = createHTTPErrorBody(throwable)
                APIState.RenderError(errorResponse)
            }

            is Exception -> {
                throwable.let {
                    it.printStackTrace()
                }
            }
        }
        return@catch
    }
}

private fun createHTTPErrorBody(throwable: HttpException): ResponseError {
    return throwable.let {
        it.printStackTrace()
        ResponseError(it.code(), Date().time, it.message())
    }
}

private fun createIOErrorBody(throwable: IOException): ResponseError {
    return throwable.let {
        it.printStackTrace()
        ResponseError(0, Date().time, it.message)
    }

}
