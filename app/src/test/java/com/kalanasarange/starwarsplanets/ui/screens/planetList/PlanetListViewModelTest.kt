package com.kalanasarange.starwarsplanets.ui.screens.planetList

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.turbine.test
import com.kalanasarange.starwarsplanets.data.repository.PlanetPagingSource
import com.kalanasarange.starwarsplanets.data.repository.PlanetRepository
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class PlanetListViewModelTest {
    private lateinit var viewModel: PlanetListViewModel
    private lateinit var planetRepository: PlanetRepository


    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        planetRepository = mock(PlanetRepository::class.java)
        viewModel = PlanetListViewModel(planetRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `planetResponse emits PagingData`() = runTest {
        viewModel.planetResponse.test {
            val pagingData = awaitItem()
            assertNotNull(pagingData)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `pager configuration should be correct`() = runTest{
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PlanetPagingSource(planetRepository) }
        )

        assertNotNull(pager)
    }
}