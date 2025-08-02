package com.easi.tictactoe.ui.screen.setup

import android.content.Context
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.easi.tictactoe.components.GradientText
import com.easi.tictactoe.model.GameMode

@Composable
fun GameModeSelector(
    modifier: Modifier = Modifier,
    context: Context,
    selectedMode: GameMode,
    onModeSelected: (GameMode) -> Unit
) {
    val gameMode = listOf(GameMode.SINGLE, GameMode.MULTI)

    val alpha by rememberInfiniteTransition()
        .animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(500),
                repeatMode = RepeatMode.Reverse
            ))

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        gameMode.forEach { mode ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        ///TODO ADD SOUND
                        onModeSelected(mode)
                    }
                    .padding(vertical = 8.dp)
            ) {
                if (selectedMode == mode) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Flèche",
                        modifier = Modifier
                            .size(24.dp)
                            .alpha(alpha),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                } else {
                    Spacer(modifier = Modifier.width(24.dp))
                }

                GradientText(text = mode.label)
            }
        }
    }
}