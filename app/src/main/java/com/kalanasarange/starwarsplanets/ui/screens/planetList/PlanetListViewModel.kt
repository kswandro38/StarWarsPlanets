package com.kalanasarange.starwarsplanets.ui.screens.planetList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kalanasarange.starwarsplanets.data.model.Planet
import com.kalanasarange.starwarsplanets.data.repository.PlanetPagingSource
import com.kalanasarange.starwarsplanets.data.repository.PlanetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Default page size
private const val PAGE_SIZE = 10

/**
 * [PlanetListViewModel] is a [ViewModel] responsible for providing a paginated list of [Planet] objects.
 *
 * It utilizes the [PlanetRepository] to fetch planet data and [Pager] to handle pagination efficiently.
 * The fetched data is exposed as a [Flow] of [PagingData] which can be consumed by UI components.
 *
 * @property planetRepository The [PlanetRepository] used to retrieve planet data.
 *
 * @constructor Creates an instance of [PlanetListViewModel].
 *
 * @param planetRepository The [PlanetRepository] implementation to use for data fetching.
 * This dependency is injected using Hilt.
 */
@HiltViewModel
class PlanetListViewModel @Inject constructor(
    private val planetRepository: PlanetRepository
) : ViewModel() {
    val planetResponse: Flow<PagingData<Planet>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PlanetPagingSource(planetRepository) }
    ).flow.cachedIn(viewModelScope)
}