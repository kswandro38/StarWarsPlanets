package com.kalanasarange.starwarsplanets.ui.screens.planetList

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kalanasarange.starwarsplanets.R
import com.kalanasarange.starwarsplanets.ui.components.ErrorBottomSheet
import com.kalanasarange.starwarsplanets.ui.components.Header
import com.kalanasarange.starwarsplanets.ui.components.PlantItemCard
import com.kalanasarange.starwarsplanets.ui.navigation.Screen
import com.kalanasarange.starwarsplanets.ui.theme.MyApplicationTheme

/**
 * [PlanetListScreen] is a composable function that displays a list of planets fetched from a remote source.
 * It utilizes Paging 3 library for efficient data loading and display.
 *
 * @param navController The navigation controller used to navigate to the planet details screen.
 *
 * Functionality:
 * - **Fetches Planet Data**: Uses [PlanetListViewModel] to fetch a list of planets using Paging 3.
 * - **Displays Planet List**: Displays the fetched planets in a [LazyColumn], each represented by a [PlantItemCard].
 * - **Pull-to-Refresh**: Implements a pull-to-refresh functionality using [PullToRefreshBox] and [rememberPullToRefreshState].
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlanetListScreen(navController: NavController) {
    val viewModel: PlanetListViewModel = hiltViewModel()
    val planets = viewModel.planetResponse.collectAsLazyPagingItems()
    val loadState = planets.loadState
    var isRefreshing by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage: String? by remember { mutableStateOf(null) }
    val pullToRefreshState = rememberPullToRefreshState()

    MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
            Column (modifier = Modifier.background(
                color = Color.White
            )){
                Header(stringResource(R.string.planet_list_screen_title))

                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    // Enable pull-to-refresh to reload data
                    PullToRefreshBox(
                        state = pullToRefreshState,
                        onRefresh = {
                            isRefreshing = true

                            // Ask page-source to refresh data when pull-to-refresh
                            planets.refresh()
                        },
                        isRefreshing = isRefreshing,
                        modifier = Modifier.testTag("pull_refresh_layout"),
                        indicator = {
                            Indicator(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .testTag("pull_refresh_indicator"),
                                isRefreshing = isRefreshing,
                                state = pullToRefreshState
                            )
                        },
                    ){

                        // Show Planet cards in LazyColumn
                        LazyColumn(
                            modifier = Modifier.testTag("planet_lazy_column")
                        ) {
                            when(loadState.refresh){
                                is LoadState.Loading -> {
                                    isRefreshing = true
                                    isError = false
                                }
                                is LoadState.Error -> {
                                    isRefreshing = false
                                    isError = true
                                    errorMessage = (loadState.refresh as LoadState.Error).error.message
                                }
                                else -> {
                                    isRefreshing = false
                                    isError = false
                                }
                            }

                            items(planets.itemCount) { index ->
                                val planet = planets[index]
                                if(planet!=null){
                                    PlantItemCard(planet, navigationCallback = { planetId ->
                                        navController.navigate(Screen.PlanetDetails.createRoute(planetId))
                                    })
                                }
                            }
                        }

                        // Show error dialog if something went wrong
                        ErrorBottomSheet(
                            isVisible = isError,
                            message = errorMessage ?: stringResource(R.string.error_common),
                            buttonText = stringResource(R.string.error_button_retry),
                            buttonCallback = {
                                isRefreshing = true
                                planets.refresh()
                            }
                        )
                    }
                }
            }
        }
    }
}