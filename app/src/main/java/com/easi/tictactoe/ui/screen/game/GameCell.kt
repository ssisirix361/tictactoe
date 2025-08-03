package com.easi.tictactoe.ui.screen.game

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import com.easi.tictactoe.model.PlayerSymbol
import com.easi.tictactoe.model.WinDirection

@Composable
fun GameCell(
    playerSymbol: PlayerSymbol,
    isWinning: Boolean,
    winDirection: WinDirection?,
    modifier: Modifier,
    onClick: () -> Unit
) {

    val isVisible = playerSymbol != PlayerSymbol.NONE

    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val rotation by animateFloatAsState(
        targetValue = if (isVisible) 0f else 180f,
        animationSpec = tween(500)
    )


    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClick() }
            .scale(if (isWinning) 1.1f else 1f)
            .drawWithContent {
                drawContent()

                if (isWinning && winDirection != null) {
                    val strokeWidth = 12f
                    val width = size.width
                    val height = size.height

                    when (winDirection) {
                        WinDirection.HORIZONTAL -> {
                            val centerY = height / 2
                            val startX = 0F
                            drawLine(
                                color = playerSymbol.color,
                                start = Offset(startX, centerY),
                                end = Offset(width, centerY),
                                strokeWidth = strokeWidth
                            )
                        }

                        WinDirection.VERTICAL -> {
                            val centerX = size.width / 2
                            drawLine(
                                color = playerSymbol.color,
                                start = Offset(centerX, 0f),
                                end = Offset(centerX, size.height),
                                strokeWidth = strokeWidth
                            )
                        }



                        WinDirection.DIAGONAL_LEFT -> {
                            val startOffset = Offset(0f, 0f)
                            val endOffset = Offset(width, height)
                            drawLine(
                                color = playerSymbol.color,
                                start = startOffset,
                                end = endOffset,
                                strokeWidth = strokeWidth
                            )
                        }

                        WinDirection.DIAGONAL_RIGHT -> {
                            val startOffset =  Offset(width, 0f)
                            val endOffset = Offset(0f, height)
                            drawLine(
                                color = playerSymbol.color,
                                start = startOffset,
                                end = endOffset,
                                strokeWidth = strokeWidth
                            )
                        }
                    }
                }
            }

        ,
        contentAlignment = Alignment.Center
    ) {

        playerSymbol.icon?.let {
            Icon(
                imageVector = it,
                contentDescription = "Play icon - O & X",
                tint = playerSymbol.color,
                modifier = Modifier
                    .size(50.dp)
                    .scale(scale)
                    .rotate(rotation)
            )
        }
    }

}