package com.easi.tictactoe.ui.screen.setup

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easi.tictactoe.model.PlayerSymbol
import com.easi.tictactoe.ui.theme.BlueGradient
import com.easi.tictactoe.ui.theme.bitCountFamily


@Composable
fun SymbolSelector(
    modifier: Modifier,
    context: Context,
    selectedSymbol: PlayerSymbol,
    onSymbolSelected: (PlayerSymbol) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PlayerSymbol.entries
            .filter{it != PlayerSymbol.NONE}
            .forEach { symbol ->
            val isSelected = selectedSymbol == symbol

            Text(
                text = symbol.display,
                fontSize = 32.sp,
                fontFamily = bitCountFamily,
                fontWeight = FontWeight.Bold,
                textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None,
                style = TextStyle(
                    brush = if (isSelected) {
                        Brush.linearGradient(
                            colors = BlueGradient
                        )
                    } else {
                        Brush.linearGradient(
                            colors = listOf(Color.Gray, Color.Gray)
                        )
                    }
                )
                ,
                modifier = Modifier
                    .clickable {
                        AudioManager.playSound(context = context, sound = SoundType.CLICK)
                        onSymbolSelected(symbol)
                    }
                    .padding(8.dp)
            )
        }
    }
}
