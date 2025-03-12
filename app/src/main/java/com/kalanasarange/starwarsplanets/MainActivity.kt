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

/**
 * [MainActivity] is the main entry point of the application.
 *
 * This activity serves as the central hub for the application's user interface and navigation.
 * It leverages Jetpack Compose for building the UI and Jetpack Compose Navigation for managing
 * the flow between different screens.
 *
 * **Key Responsibilities:**
 *  - **Navigation Management:** Initializes and manages the navigation graph using
 *    `NavHost` and `rememberNavController`. This defines the different screens in the app
 *    and how to navigate between them.
 *  - **Route Definitions:** Defines the different routes (`Screen.PlanetList.route`,
 *    `Screen.PlanetDetails.route`) within the navigation graph. Each route corresponds to a
 *    specific composable screen.
 *  - **Composable Destinations:** Specifies the composable destinations for each route.
 *     - `PlanetListScreen`: Displays the list of planets. This is the start destination.
 *     - `PlanetDetailsScreen`: Displays the details of a selected planet.
 *  - **Navigation Arguments:** Handles passing arguments between screens using `navArgument`.
 *    In this case, it passes `planetId` (of type `Int`) to `PlanetDetailsScreen` to
 */
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