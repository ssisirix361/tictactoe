package com.easi.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.easi.tictactoe.ui.screen.setup.GameSetupScreen
import com.easi.tictactoe.ui.theme.TictactoeTheme
import com.easi.tictactoe.utils.AppMusicLifecycleHandler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        AppMusicLifecycleHandler.register()
        setContent {
            TictactoeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentColor = MaterialTheme.colorScheme.background) { paddingValues ->
                    GameSetupScreen(modifier = Modifier.padding(paddingValues), navController = null)
                }
            }
        }
    }
}

