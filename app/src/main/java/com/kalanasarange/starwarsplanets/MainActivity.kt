package com.kalanasarange.starwarsplanets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kalanasarange.starwarsplanets.ui.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint
import com.kalanasarange.starwarsplanets.ui.screens.planetList.PlanetListScreen
import com.kalanasarange.starwarsplanets.ui.screens.planetDetails.PlanetDetailsScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = Screen.PlanetList.route){
                composable(Screen.PlanetList.route) {
                    PlanetListScreen(navController)
                }
                composable(
                    route = Screen.PlanetDetails.route,
                    arguments = listOf(navArgument("planetId"){ type = NavType.IntType })
                ) { backStackEntry ->
                    val planetId = backStackEntry.arguments?.getInt("planetId") ?: -1
                    PlanetDetailsScreen(navController, planetId)
                }
            }
        }
    }
}