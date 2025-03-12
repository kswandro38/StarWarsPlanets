package com.kalanasarange.starwarsplanets.ui.screens.planetDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kalanasarange.starwarsplanets.api.ResponseState
import com.kalanasarange.starwarsplanets.data.model.Planet
import com.kalanasarange.starwarsplanets.data.repository.PlanetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class PlanetDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PlanetDetailsViewModel

    // Mock planetRepository
    private val planetRepository: PlanetRepository = mock()

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PlanetDetailsViewModel(planetRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loadPlanetDetails emits Success`() = runTest {
        // Fake Planet
        val planet = Planet(
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
        )
        val successResponse = ResponseState.Success(planet)

        whenever(planetRepository.getPlanetDetails(planet.id)).thenReturn(successResponse)

        // Collect emissions
        val emissions = mutableListOf<ResponseState<Planet>>()
        val job = launch {
            viewModel.planetDetailsResponse.collect { emissions.add(it) }
        }

        // When
        viewModel.loadPlanetDetails(planet.id)
        advanceUntilIdle() // Let coroutines run

        // Then
        assert(emissions[1] == successResponse)

        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loadPlanetDetails emits Error`() = runTest {
        // Given
        val planetId = 1
        val errorResponse = ResponseState.Error<Planet>(
            errorTitle = "Network error",
            errorMessage = "Failed to fetch planet details"
        )

        whenever(planetRepository.getPlanetDetails(planetId)).thenReturn(errorResponse)

        // Collect emissions
        val emissions = mutableListOf<ResponseState<Planet>>()
        val job = launch {
            viewModel.planetDetailsResponse.collect { emissions.add(it) }
        }

        // When
        viewModel.loadPlanetDetails(planetId)
        advanceUntilIdle()

        // Then
        assert(emissions[1] == errorResponse)

        job.cancel()
    }
}