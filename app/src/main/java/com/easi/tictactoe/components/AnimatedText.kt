package com.easi.tictactoe.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun AnimatedText(
    modifier: Modifier = Modifier,
    fullText: String,
    fontSize: TextUnit = 25.sp,
    delayMillis: Long = 50L
) {
    var textToDisplay by remember { mutableStateOf("") }

    LaunchedEffect(fullText) {
        textToDisplay = ""
        fullText.forEachIndexed { index, _ ->
            textToDisplay = fullText.substring(0, index + 1)
            delay(delayMillis)
        }
    }

    GradientText(modifier = modifier, text = textToDisplay, fontSize = fontSize)
}
