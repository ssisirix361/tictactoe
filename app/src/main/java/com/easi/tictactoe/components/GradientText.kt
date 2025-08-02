package com.easi.tictactoe.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.easi.tictactoe.ui.theme.BlueGradient
import com.easi.tictactoe.ui.theme.bitCountFamily

@Composable
fun GradientText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 25.sp,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = fontSize,
            fontFamily = bitCountFamily,
            brush = Brush.linearGradient(colors = BlueGradient)
        )
    )
}
