package com.kalanasarange.starwarsplanets.ui.screens.planetDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalanasarange.starwarsplanets.api.ResponseState
import com.kalanasarange.starwarsplanets.data.model.Planet
import com.kalanasarange.starwarsplanets.data.repository.PlanetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [PlanetDetailsViewModel] is a ViewModel responsible for fetching and providing
 * details of a specific planet. It interacts with the [PlanetRepository] to
 * retrieve the planet data and exposes the result as a state flow.
 *
 * @property planetRepository The repository responsible for fetching planet data
 * which inject through hilt
 */
@HiltViewModel
class PlanetDetailsViewModel @Inject constructor(
    private val planetRepository: PlanetRepository
) : ViewModel() {
    private val _PlanetDetailsResponse =
        MutableStateFlow<ResponseState<Planet>>(ResponseState.Idle())
    val planetDetailsResponse = _PlanetDetailsResponse.asStateFlow()

    fun loadPlanetDetails(planetId: Int) {
        viewModelScope.launch {
            with(_PlanetDetailsResponse) {
                // Update loading state of the flow
                tryEmit(ResponseState.Loading())

                //Update the success state with data array
                tryEmit(planetRepository.getPlanetDetails(planetId))
            }
        }
    }
}