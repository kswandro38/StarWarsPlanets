package com.kalanasarange.starwarsplanets.api

import com.kalanasarange.starwarsplanets.BuildConfig
import com.kalanasarange.starwarsplanets.data.model.Planet
import com.kalanasarange.starwarsplanets.data.model.PlanetListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Represents the API interface for interacting with a remote server to retrieve planet data.
 * This interface defines the endpoints and data structures used for communication with the API.
 */
interface StarWarsAPI {
    companion object {
        // The base URL of the API. This value is fetched from the BASE_URL property
        // in the gradle.properties file for security purposes.
        const val BASE_URL = BuildConfig.BASE_URL
    }

    /**
     * Retrieves a list of planets from the API.
     *
     * @param page - Page number of the planets to retrieve.
     * @return [PlanetListResponse] object containing the list of planets.
     */
    @GET("planets")
    suspend fun getPlanets(@Query("page") page: Int): PlanetListResponse


    /**
     * Retrieves the planet details from the API.
     *
     * @param id - Planet ID
     * @return [Planet] object containing the details of the required planet.
     */
    @GET("planets/{id}")
    suspend fun getPlanetDetails(@Path("id") id: Int): Planet
}