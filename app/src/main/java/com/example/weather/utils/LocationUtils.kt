package com.example.weather.utils

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import java.util.concurrent.TimeUnit

object LocationUtils {
    @SuppressLint("MissingPermission")
    fun requestLocationResultCallback(
        fusedLocationProviderClient: FusedLocationProviderClient,
        locationResultCallback: (LocationResult) -> Unit
    ) {

        val locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                locationResultCallback(locationResult)
                fusedLocationProviderClient.removeLocationUpdates(this)
            }
        }

        val request = LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, TimeUnit.MINUTES.toMillis(10))
            .setMinUpdateDistanceMeters(1000F)
            .build()
        Looper.myLooper()?.let { looper ->
            fusedLocationProviderClient.requestLocationUpdates(
                request,
                locationCallback,
                looper
            )
        }
    }
}