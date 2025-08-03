package com.easi.tictactoe.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.easi.tictactoe.ui.theme.cyan
import com.easi.tictactoe.ui.theme.red


enum class PlayerSymbol(val display: String, var color : Color, var icon : ImageVector?) {
    CROSS(display = "X", color = red, icon =  Icons.Default.Close),
    CIRCLE(display = "O", color = cyan, icon = Icons.Default.RadioButtonUnchecked),
    NONE("", Color.Transparent, icon = null)
}