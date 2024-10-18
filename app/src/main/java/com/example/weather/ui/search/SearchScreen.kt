package com.example.weather.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.weather.R
import com.example.weather.constants.ScreenConstants
import com.example.weather.data.DisplayAddress
import com.example.weather.route.NavigationAction
import com.example.weather.route.executeNavigationAction
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.searchScreenEntry(
    navController: NavController
) {
    composable(ScreenConstants.SEARCH_SCREEN) {
        CreateSearchScreen(
            sendNavigationAction = navController::executeNavigationAction
        )
    }
}
@Composable
fun CreateSearchScreen(
    searchViewModel: SearchViewModel = koinViewModel(),
    sendNavigationAction: (NavigationAction) -> Unit
) {
    val placesList = searchViewModel.placesList.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxWidth())
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    searchViewModel.siteSearchText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, bottom = 16.dp)
                    .focusRequester(focusRequester),
                shape = RoundedCornerShape(18.dp),
                placeholder = { Text("Search city") }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f),
            ) {
                itemsIndexed(items = placesList.value) { index, placeDetails ->
                    searchPlacesListItem(
                        DisplayAddress(
                            name = placeDetails.name.toString(),
                            latitude = placeDetails.lat.toString(),
                            longitude = placeDetails.lon.toString(),
                            state = placeDetails.state.toString(),
                            country = placeDetails.country.toString()
                        )
                    ) {
                        searchViewModel.saveSelectedPlacesWeather(placeDetails)
                        sendNavigationAction.invoke(NavigationAction.NavigateBack)
                    }
                }
            }
        }
    }

}

@Composable
fun searchPlacesListItem(
    placeDetails: DisplayAddress,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = placeDetails.getFullAddress(),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(400),
                    color = colorResource(id = R.color.white),
                )
            )
        }
    }
}