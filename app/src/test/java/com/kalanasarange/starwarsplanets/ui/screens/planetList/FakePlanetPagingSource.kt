package com.kalanasarange.starwarsplanets.ui.screens.planetList

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kalanasarange.starwarsplanets.data.model.Planet

class FakePlanetPagingSource(private val planets: List<Planet>) : PagingSource<Int, Planet>() {
    override fun getRefreshKey(state: PagingState<Int, Planet>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Planet> {
        return LoadResult.Page(
            data = planets,
            prevKey = null,
            nextKey = null
        )
    }
}