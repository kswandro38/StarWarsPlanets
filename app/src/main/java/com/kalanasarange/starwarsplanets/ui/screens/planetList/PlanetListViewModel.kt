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