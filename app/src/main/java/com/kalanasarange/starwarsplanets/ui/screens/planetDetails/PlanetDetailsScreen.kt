package com.kalanasarange.starwarsplanets.ui.screens.planetDetails

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kalanasarange.starwarsplanets.R
import com.kalanasarange.starwarsplanets.api.ResponseState
import com.kalanasarange.starwarsplanets.data.model.Planet
import com.kalanasarange.starwarsplanets.ui.components.ErrorBottomSheet
import com.kalanasarange.starwarsplanets.ui.components.Header
import com.kalanasarange.starwarsplanets.ui.components.Loader
import com.kalanasarange.starwarsplanets.ui.theme.MyApplicationTheme

/**
 * Displays the detailed information about a specific planet.
 *
 * This composable function fetches and displays the details of a planet based on the provided `planetId`.
 * It utilizes a [PlanetDetailsViewModel] to handle data fetching and manages the UI state
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlanetDetailsScreen(navController: NavController, planetId: Int) {
    val viewModel: PlanetDetailsViewModel = hiltViewModel()
    var isRefreshing by remember { mutableStateOf(false) }
    var responseState: ResponseState<Planet> by remember { mutableStateOf(ResponseState.Idle()) }
    var planet: Planet? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        viewModel.loadPlanetDetails(planetId)

        // Collect Planet details API response from flow
        viewModel.planetDetailsResponse.collect { response ->
            responseState = response
        }
    }

    MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(0.dp)
            ) {
                Header(
                    title = stringResource(R.string.planet_details_screen_title),
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )

                when (responseState) {
                    is ResponseState.Idle -> {
                        // Do nothing on idle state
                    }
                    is ResponseState.Loading -> {
                        // Show loading state when data loading
                        Loader(true)
                    }

                    is ResponseState.Success -> {
                        // Successfully fetched the planet data & manipulate the UI
                        planet = (responseState as ResponseState.Success<Planet>).data
                        isRefreshing = false
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Column {
                                // Planet Image
                                AsyncImage(
                                    model = planet?.imageUrl,
                                    contentDescription = "Planet Image",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                        .background(Color.Gray)
                                )

                                // Planet Name
                                Text(
                                    text = planet?.name ?: "",
                                    fontSize = 35.sp,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        top = 8.dp
                                    )
                                )

                                // Orbital period text (days)
                                Text(text = stringResource(R.string.orbital_Period, planet?.orbitalPeriod ?: "-"),
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        top = 5.dp
                                    )
                                )

                                //Gravity text
                                Text(text = stringResource(R.string.gravity, planet?.gravity ?: R.string.unknown),
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        top = 5.dp
                                    )
                                )
                            }
                        }
                    }

                    is ResponseState.Error -> {
                        // Something went worng and show the error popup
                        ErrorBottomSheet(
                            isVisible = true,
                            message = (responseState as ResponseState.Error).errorMessage,
                            buttonText = stringResource(R.string.error_button_retry),
                            buttonCallback = {
                                isRefreshing = true
                                viewModel.loadPlanetDetails(planetId)
                            }
                        )
                    }
                }
            }
        }
    }
}