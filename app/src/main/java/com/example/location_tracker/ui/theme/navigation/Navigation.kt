package com.example.location_tracker.ui.theme.navigation

import LocationsScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.location_tracker.ui.theme.screens.locations.LocationsViewModel
import com.example.location_tracker.ui.theme.screens.visits.VisitsScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Locations : Screen("locations", "Locations", Icons.Filled.LocationOn)
    object Visits : Screen("visits/{locationId}", "Visits", Icons.Filled.Place)
}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Locations.route) {

        composable(
            route = Screen.Locations.route
        ) {

            val viewModel: LocationsViewModel = hiltViewModel()

            LocationsScreen(
                navController = navController,
                locationId = -1,
                viewModel = viewModel
            )
        }


        composable(
            route = Screen.Visits.route,
            arguments = listOf(navArgument("locationId") { type = NavType.LongType })
        ) { backStackEntry ->
            val locationId = backStackEntry.arguments?.getLong("locationId") ?: -1
            VisitsScreen(
                locationId = locationId,
                navController = navController,
                onNavigateToLocations = { navController.navigate(Screen.Locations.route) }
            )
        }
    }
}
