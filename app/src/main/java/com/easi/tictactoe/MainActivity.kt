package com.easi.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.easi.tictactoe.ui.navigation.NavRoutes
import com.easi.tictactoe.ui.screen.setup.GameSetupScreen
import com.easi.tictactoe.ui.screen.splash.SplashScreen
import com.easi.tictactoe.ui.theme.DarkBlue
import com.easi.tictactoe.ui.theme.TictactoeTheme
import com.easi.tictactoe.utils.AppMusicLifecycleHandler
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge to edge, UI display

        AppMusicLifecycleHandler.register()
        setContent {
            val systemUiController = rememberSystemUiController()
            var showSplash by remember { mutableStateOf(true) }
            val navController = rememberNavController()

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = DarkBlue,
                    darkIcons = false
                )
            }
            TictactoeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentColor = MaterialTheme.colorScheme.background) { paddingValues ->
                    if (showSplash) {
                        SplashScreen {
                            showSplash = false
                        }
                    } else {
                        NavRoutes(navController)
                    }
                }
            }
        }
    }
}

