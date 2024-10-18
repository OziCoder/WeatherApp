package com.example.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.weather.constants.ScreenConstants
import com.example.weather.ui.landing.LandingViewModel
import com.example.weather.ui.landing.landingScreenEntry
import com.example.weather.ui.search.searchScreenEntry
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    private lateinit var landingViewModel: LandingViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            landingViewModel = getViewModel<LandingViewModel>()
            GetPermission()
        }
    }

    @Composable
    fun GetPermission() {
        var locationPermissionsGranted by remember {
            mutableStateOf(
                areLocationPermissionsAlreadyGranted()
            )
        }
        if (locationPermissionsGranted) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            landingViewModel.gotoInitialScreen()
        }
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val locationPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                locationPermissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
                    acc && isPermissionGranted
                }
                if (!locationPermissionsGranted) {
                    Toast.makeText(this, R.string.location_permission,Toast.LENGTH_SHORT).show()
                } else {
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                    landingViewModel.gotoInitialScreen()
                }
            })
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(key1 = lifecycleOwner, effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START &&
                    !locationPermissionsGranted
                ) {
                    locationPermissionLauncher.launch(locationPermissions)
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        })
        val advanceToScreen = landingViewModel.initialScreen.collectAsState()
        when (advanceToScreen.value) {
            ScreenConstants.LANDING_SCREEN -> {
                WeatherApp()
            }
        }
    }

    @Composable
    fun WeatherApp() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = ScreenConstants.LANDING_SCREEN
        ) {
            landingScreenEntry(navController, fusedLocationProviderClient)
            searchScreenEntry(navController)
        }
    }

    private fun areLocationPermissionsAlreadyGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}