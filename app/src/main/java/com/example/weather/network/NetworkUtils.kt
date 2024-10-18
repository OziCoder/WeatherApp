package com.example.weather.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.weather.application.WeatherApplication

@SuppressLint("StaticFieldLeak")
object NetworkUtils {

    fun isNetworkConnected(): Boolean {
        try {
            val connectivityManager =
                WeatherApplication.getBaseApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager

            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }
            }
        }
        catch (e :Exception){
            e.printStackTrace()
        }
        return false
    }
}
