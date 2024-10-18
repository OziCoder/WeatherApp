package com.example.weather.ui.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weather.R
import com.example.weather.base.ComposeLayout
import com.example.weather.base.PreferencesManager
import com.example.weather.constants.ScreenConstants
import com.example.weather.route.NavigationAction
import com.example.weather.route.executeNavigationAction
import com.example.weather.ui.search.SearchScreenRoute
import com.google.android.gms.location.FusedLocationProviderClient
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.landingScreenEntry(
    navController: NavController,
    fusedLocationProviderClient: FusedLocationProviderClient
) {
    composable(ScreenConstants.LANDING_SCREEN) {
        CreateLandingScreen(
            fusedLocationProviderClient,
            sendNavigationAction = navController::executeNavigationAction
        )
    }
}

@Composable
fun CreateLandingScreen(
    fusedLocationProviderClient: FusedLocationProviderClient,
    landingViewModel: LandingViewModel = koinViewModel(),
    sendNavigationAction: (NavigationAction) -> Unit
) {
    val context = LocalContext.current
    val weather = landingViewModel.weatherData.collectAsState()
    val city = landingViewModel.cityData.collectAsState()

    /** Check to access the last searched location and retrieve weather data
     * else by accessing the current location **/
    val preferencesManager = PreferencesManager(context)
    preferencesManager.savedLocation.let {
        if (it != null) {
            landingViewModel.getSelectedWeather(it.lat.toString(), it.lon.toString())
        } else {
            landingViewModel.getCurrentLocation(fusedLocationProviderClient)
        }
    }

    ComposeLayout(
        viewModel = landingViewModel,
        sendNavigationAction = sendNavigationAction
    ) { actionEmitter ->
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.land_bg))
                .fillMaxSize()
        ) {
            /** View to show the searched location **/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable {
                        sendNavigationAction.invoke(NavigationAction.NavigateTo(SearchScreenRoute().path))
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = city.value.name.toString(), fontSize = 30.sp,
                    fontWeight = FontWeight(500),
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            /** View to show the weather details **/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(weather.value.logoUrl())
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_arrow_down),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(75.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = weather.value.main?.temp.toString(), fontSize = 45.sp,
                    fontWeight = FontWeight(500),
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = stringResource(id = R.string.fahrenheit), fontSize = 25.sp,
                    fontWeight = FontWeight(500),
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column() {
                    if (weather.value.weather.size > 0) {
                        Text(
                            text = weather.value.weather[0].description.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500),
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = stringResource(id = R.string.feels_like).format(weather.value.main?.feelsLike.toString()),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = Color.White
                    )
                    Text(
                        text = stringResource(id = R.string.high_low_temp).format(
                            weather.value.main?.tempMax.toString(),
                            weather.value.main?.tempMin.toString()
                        ),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = Color.White
                    )
                }
            }
        }
    }
}
