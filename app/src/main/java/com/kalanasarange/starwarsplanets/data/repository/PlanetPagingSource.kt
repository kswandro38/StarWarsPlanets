package com.kalanasarange.starwarsplanets.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kalanasarange.starwarsplanets.api.ResponseState
import com.kalanasarange.starwarsplanets.data.model.Planet
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

class PlanetPagingSource @Inject constructor(
    private val planetRepository: PlanetRepository
) : PagingSource<Int, Planet>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Planet> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val responseState = planetRepository.getPlanets(position)

            when(responseState){
                is ResponseState.Success -> {
                    val planets = responseState.data.planets
                    LoadResult.Page(
                        data = planets,
                        prevKey = if (position == STARTING_PAGE_INDEX) null else position.minus(1),
                        nextKey = if(responseState.data.next==null) null else position.plus(1)
                    )
                }
                is ResponseState.Error -> {
                    LoadResult.Error<Int, Planet>(Exception(responseState.errorMessage))
                }
                is ResponseState.Loading -> {
                    LoadResult.Error<Int, Planet>(Exception("Loading"))
                }
                is ResponseState.Idle -> {
                    LoadResult.Error<Int, Planet>(Exception("Idle"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    /**
     * Returns the key for the next page to be loaded when refreshing.
     *
     * This method is used by the Paging library to determine the starting point
     * for loading data when the planet performs a refresh action
     *
     * @param state The current state of the Paging system.
     * @return The page key to refresh from or null if no valid refresh key exists.
     */
    override fun getRefreshKey(state: PagingState<Int, Planet>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}