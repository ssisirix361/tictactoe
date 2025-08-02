package com.easi.tictactoe.model

import androidx.compose.ui.graphics.Color
import com.easi.tictactoe.ui.theme.cyan
import com.easi.tictactoe.ui.theme.red


enum class PlayerSymbol(val display: String, var color : Color) {
    CROSS(display = "X", color = red),
    CIRCLE(display = "O", color = cyan),
    NONE("", Color.Transparent)
}