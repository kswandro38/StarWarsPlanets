package com.kalanasarange.starwarsplanets.ui.navigation

sealed class Screen(val route: String) {
    object PlanetList: Screen("planet_list")
    object PlanetDetails: Screen("planet_details/{planetId}"){
        fun createRoute(planetId: Int) = "planet_details/$planetId"
    }
}