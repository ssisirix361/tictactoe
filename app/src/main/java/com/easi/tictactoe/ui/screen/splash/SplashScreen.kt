package com.easi.tictactoe.ui.screen.splash

import OXOLoadingAnimation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.easi.tictactoe.ui.theme.TictactoeTheme

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onSplashComplete: () -> Unit
) {
    var animationDone by remember { mutableStateOf(false) }

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OXOLoadingAnimation(
                modifier = Modifier.fillMaxSize()
            ) {
                animationDone = true
            }

        }

    LaunchedEffect(animationDone) {
        if (animationDone) {
            onSplashComplete()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    TictactoeTheme {
        SplashScreen {}
    }
}
