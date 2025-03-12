package com.kalanasarange.starwarsplanets.data.repository

import android.util.Log
import com.kalanasarange.starwarsplanets.api.ResponseState
import com.kalanasarange.starwarsplanets.api.StarWarsAPI
import com.kalanasarange.starwarsplanets.data.model.Planet
import com.kalanasarange.starwarsplanets.data.model.PlanetListResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
class PlanetRepositoryTest {
    private lateinit var repository: PlanetRepository
    private lateinit var api: StarWarsAPI

    @Before
    fun setup() {
        // Setup for Log mocking
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        Dispatchers.setMain(StandardTestDispatcher())
        api = mockk<StarWarsAPI>()
        repository = PlanetRepository(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPlanets should return Success with PlanetListResponse on successful API call`() = runTest {
        // Prepare mock data
        val mockResponse = PlanetListResponse(
            count = 2,
            next = null,
            previous = null,
            planets = arrayListOf(
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
                )
            )
        )

        // Mock the API call
//        `when`(api.getPlanets(page = 1)).thenReturn(mockResponse)
        coEvery { api.getPlanets(1) } returns mockResponse

        // Call the repository function
        val result = repository.getPlanets(page = 1)

        // Verify the result
        assertEquals(ResponseState.Success(mockResponse), result)
    }

    @Test
    fun `getPlanets should return Error on API exception`() = runTest {
        // Mock the API call to throw an exception using Mokk
        coEvery { api.getPlanets(1) } throws SocketTimeoutException("Network error")

        // Call the repository function
        val result = repository.getPlanets(1)

        // Verify the result
        assertEquals(true, result is ResponseState.Error)
    }

    @Test
    fun `getPlanetDetails should return Success with Planet on successful API call`() = runTest {
        // Prepare mock data
        val mockPlanet = Planet(
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

        // Mock the API call
        coEvery { api.getPlanetDetails(id = 1) } returns mockPlanet

        // Call the repository function
        val result = repository.getPlanetDetails(1)

        // Verify the result
        assertEquals(ResponseState.Success(mockPlanet), result)
    }

    @Test
    fun `getPlanetDetails should return Error on API exception`() = runTest {
        // Mock the API call to throw an exception using Mokk
        coEvery { api.getPlanetDetails(1) } throws IOException("Planet not found")

        // Call the repository function
        val result = repository.getPlanetDetails(1)

        // Verify the result
        assertEquals(true, result is ResponseState.Error)
        assertEquals("Planet not found", (result as ResponseState.Error).errorMessage)
    }
}