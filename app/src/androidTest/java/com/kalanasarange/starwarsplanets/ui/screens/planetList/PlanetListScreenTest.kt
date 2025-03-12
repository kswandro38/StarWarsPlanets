package com.kalanasarange.starwarsplanets.ui.screens.planetList

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kalanasarange.starwarsplanets.MainActivity
import com.kalanasarange.starwarsplanets.data.model.Planet
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PlanetListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    // Mock dependencies
    private lateinit var navController: NavController
    private lateinit var viewModel: PlanetListViewModel

    private val samplePlanets = arrayListOf(
        Planet(
            name = "Tatooine",
            rotationPeriod = "23",
            orbitalPeriod = "304",
            diameter = "10465",
            climate = "arid",
            gravity = "1 standard",
            terrain = "desert",
            surfaceWater = "1",
            population = "200000",
            url = "https://swapi.dev/api/planets/1/"
        ),
        Planet(
            name = "Alderaan",
            rotationPeriod = "24",
            orbitalPeriod = "364",
            diameter = "12500",
            climate = "temperate",
            gravity = "2.5 standard",
            terrain = "grasslands, mountains",
            surfaceWater = "40",
            population = "2000000000",
            url = "https://swapi.dev/api/planets/2/"
        ),
        Planet(
            name = "Yavin IV",
            rotationPeriod = "20",
            orbitalPeriod = "4818",
            diameter = "10200",
            climate = "temperate, tropical",
            gravity = "1 standard",
            terrain = "jungle, rainforests",
            surfaceWater = "8",
            population = "1000",
            url = "https://swapi.dev/api/planets/3/"
        )
    )

    @Before
    fun setup() {
        // Initialize mocks
        hiltRule.inject()
        navController = mockk(relaxed = true)
        viewModel = mockk(relaxed = true)

        // Mock the paging data flow
        val pagingDataFlow = MutableStateFlow(PagingData.from(samplePlanets))
        every { viewModel.planetResponse } returns pagingDataFlow

        // Set up the compose content
        composeTestRule.setContent {
            // You'll need to create a TestNavHostController or modify your PlanetListScreen
            // to accept a ViewModel parameter for testing
            PlanetListScreen(navController = navController)
        }
    }

    @Test
    fun testScreenTitle_isDisplayed() {
        // Using a simple text match instead of resource lookup
        composeTestRule.onNodeWithText("StarWars Planets") // Replace with your actual title
            .assertIsDisplayed()
    }
}