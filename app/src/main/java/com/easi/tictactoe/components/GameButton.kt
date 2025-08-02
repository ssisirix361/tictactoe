package com.easi.tictactoe.components

import AudioManager
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp


@Composable
fun GameButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    val context = LocalContext.current

    Button(
        onClick = {
            AudioManager.playSound(context = context, sound = SoundType.CLICK)
            onClick()
        },
        modifier = modifier,
    ) {
        GradientText(
            text = text.uppercase(),
            fontSize = 20.sp
        )
    }
}

