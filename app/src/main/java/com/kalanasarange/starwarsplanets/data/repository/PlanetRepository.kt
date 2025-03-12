package com.kalanasarange.starwarsplanets.data.repository

import com.kalanasarange.starwarsplanets.api.StarWarsAPI
import com.kalanasarange.starwarsplanets.api.ResponseState
import com.kalanasarange.starwarsplanets.data.model.Planet
import com.kalanasarange.starwarsplanets.data.model.PlanetListResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class acts as a single source of truth for planet data and
 * provides methods to fetch planets from the API. It handles network
 * requests and potential exceptions, returning a ResponseState
 * object to indicate the outcome of the operation.
 */


@Singleton
open class PlanetRepository @Inject constructor(
    private val starWarsAPI: StarWarsAPI
): Repository() {

    /**
     * Retrieves a list of planets from the API.
     *
     * This function makes a network request to fetch planet data and
     * returns a ResponseState object to indicate success or failure.
     * In case of an error, the exception is handled and a corresponding
     * error state is returned.
     *
     * @return ResponseState<PlanetResponse> - A ResponseState object containing
     * either a list of planets in a ResponseState.Success state or an error message
     * in a ResponseState.
     */
    suspend fun getPlanets(page: Int): ResponseState<PlanetListResponse> {
        return try{
            ResponseState.Success(
                starWarsAPI.getPlanets(page)
            )
        } catch (exception: Exception){
            handleException(exception)
        }
    }

    open suspend fun getPlanetDetails(planetId: Int): ResponseState<Planet> {
        return try{
            ResponseState.Success(
                starWarsAPI.getPlanetDetails(planetId)
            )
        } catch (exception: Exception){
            handleException(exception)
        }
    }
}