package com.easi.tictactoe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.easi.tictactoe.ui.screen.setup.GameSetupScreen
import com.easi.tictactoe.viewmodel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictactoe.GameScreen

object NavRoutes {
    const val ROOT = "root_graph"
    const val GAME_SETUP = "game_setup"
    const val MATCH_SCREEN = "match_screen"

}

@Composable
fun NavRoutes(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.GAME_SETUP,
        route = NavRoutes.ROOT
    ) {
        composable(NavRoutes.GAME_SETUP) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("root_graph")
            }
            val viewModel: GameViewModel = viewModel(parentEntry)
            GameSetupScreen(viewModel = viewModel, navController = navController)
        }

        composable(NavRoutes.MATCH_SCREEN) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("root_graph")
            }
            val viewModel: GameViewModel = viewModel(parentEntry)
            GameScreen(viewModel = viewModel, navController = navController)
        }
    }
}

