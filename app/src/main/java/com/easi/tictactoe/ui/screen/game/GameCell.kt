package com.easi.tictactoe.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
import com.easi.tictactoe.model.PlayerSymbol
import com.easi.tictactoe.model.WinDirection

@Composable
fun GameCell(
    playerSymbol: PlayerSymbol,
    isWinning: Boolean,
    winDirection: WinDirection?,
    modifier: Modifier,
    row: Int,
    col: Int,
    boardSize: Int = 3,
    onClick: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(playerSymbol) {
        if (playerSymbol != PlayerSymbol.NONE) {
            isVisible = true
        } else {
            isVisible = false
        }
    }

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
                            val startX = if (col == 0) 0f else 0f
                            val endX = if (col == boardSize - 1) width else width
                            drawLine(
                                color = playerSymbol.color,
                                start = Offset(startX, centerY),
                                end = Offset(endX, centerY),
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
                            val startOffset = if (row == 0 && col == 0) Offset(0f, 0f) else Offset(0f, 0f)
                            val endOffset = if (row == boardSize - 1 && col == boardSize - 1) Offset(width, height) else Offset(width, height)
                            drawLine(
                                color = playerSymbol.color,
                                start = startOffset,
                                end = endOffset,
                                strokeWidth = strokeWidth
                            )
                        }

                        WinDirection.DIAGONAL_RIGHT -> {
                            val startOffset = if (row == 0 && col == boardSize - 1) Offset(width, 0f) else Offset(width, 0f)
                            val endOffset = if (row == boardSize - 1 && col == 0) Offset(0f, height) else Offset(0f, height)
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

        if (playerSymbol != PlayerSymbol.NONE) {
            Icon(
                imageVector = if (playerSymbol == PlayerSymbol.CROSS) Icons.Default.Close
                else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = playerSymbol.color,
                modifier = Modifier
                    .size(50.dp)
                    .scale(scale)
                    .rotate(rotation)
            )
        }
    }

}