import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easi.tictactoe.model.PlayerSymbol
import com.easi.tictactoe.ui.theme.lightGray
import kotlinx.coroutines.delay

@Composable
fun OXOLoadingAnimation(
    modifier: Modifier = Modifier,
    animationSpeed: Long = 300L ,
    onSplashComplete: () -> Unit
) {
    val symbolAnimations = remember { mutableStateMapOf<Pair<Int, Int>, Animatable<Float, AnimationVector1D>>() }
    var gameBoard by remember { mutableStateOf(Array(3) { Array(3) { PlayerSymbol.NONE } }) }

    var isWinning by remember { mutableStateOf(false) }
    val winningAnimation = animateFloatAsState(
        targetValue = if (isWinning) 1.3f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ), label = "winning"
    )

    LaunchedEffect(true) {
            playGame(
                gameBoard = { newBoard -> gameBoard = newBoard },
                symbolAnimations = symbolAnimations,
                setWinning = { isWinning = it },
                animationSpeed = animationSpeed
            ) {
                onSplashComplete()
            }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(200.dp)
        ) {
            val canvasSize = size.minDimension
            val cellSize = canvasSize / 3f
            val gridOffset = (size.width - canvasSize) / 2f to (size.height - canvasSize) / 2f

            drawGrid(cellSize, gridOffset)
            drawSymbols(
                gameBoard = gameBoard,
                cellSize = cellSize,
                gridOffset = gridOffset,
                symbolAnimations = symbolAnimations,
                winningScale = if (isWinning) winningAnimation.value else 1f
            )
        }
    }
}

private fun DrawScope.drawGrid(
    cellSize: Float,
    gridOffset: Pair<Float, Float>
) {
    val (offsetX, offsetY) = gridOffset

    // Lignes verticales
    for (i in 1..2) {
        val x = offsetX + i * cellSize
        drawLine(
            color = lightGray,
            start = Offset(x, offsetY),
            end = Offset(x, offsetY + cellSize * 3),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )
    }

    // Lignes horizontales
    for (i in 1..2) {
        val y = offsetY + i * cellSize
        drawLine(
            color = lightGray,
            start = Offset(offsetX, y),
            end = Offset(offsetX + cellSize * 3, y),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

private fun DrawScope.drawSymbols(
    gameBoard: Array<Array<PlayerSymbol>>,
    cellSize: Float,
    gridOffset: Pair<Float, Float>,
    symbolAnimations: Map<Pair<Int, Int>, Animatable<Float, AnimationVector1D>>,
    winningScale: Float
) {
    val (offsetX, offsetY) = gridOffset

    for (row in 0..2) {
        for (col in 0..2) {
            val symbol = gameBoard[row][col]
            if (symbol != PlayerSymbol.NONE) {
                val key = Pair(row, col)
                val animationProgress = symbolAnimations[key]?.value ?: 0f

                val centerX = offsetX + col * cellSize + cellSize / 2f
                val centerY = offsetY + row * cellSize + cellSize / 2f

                val scale = if (isWinningCell(row, col)) {
                    animationProgress * winningScale
                } else {
                    animationProgress
                }

                scale(scale, Offset(centerX, centerY)) {
                    when (symbol) {
                        PlayerSymbol.CROSS -> drawX(centerX, centerY, cellSize * 0.3f, symbol.color)
                        PlayerSymbol.CIRCLE -> drawO(centerX, centerY, cellSize * 0.3f, symbol.color)
                        else -> {}
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawX(centerX: Float, centerY: Float, radius: Float, color: Color) {
    val offset = radius * 0.7f
    drawLine(
        color = color,
        start = Offset(centerX - offset, centerY - offset),
        end = Offset(centerX + offset, centerY + offset),
        strokeWidth = 6.dp.toPx(),
        cap = StrokeCap.Round
    )
    drawLine(
        color = color,
        start = Offset(centerX + offset, centerY - offset),
        end = Offset(centerX - offset, centerY + offset),
        strokeWidth = 6.dp.toPx(),
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawO(centerX: Float, centerY: Float, radius: Float, color: Color) {
    drawCircle(
        color = color,
        radius = radius,
        center = Offset(centerX, centerY),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
    )
}

private fun isWinningCell(row: Int, col: Int): Boolean {
    return (row == 1 && col == 1) || (row == 0 && col == 0) || (row == 2 && col == 2)
}

private suspend fun playGame(
    gameBoard: (Array<Array<PlayerSymbol>>) -> Unit,
    symbolAnimations: MutableMap<Pair<Int, Int>, Animatable<Float, AnimationVector1D>>,
    setWinning: (Boolean) -> Unit,
    animationSpeed: Long,
    onFinished: () -> Unit
) {
    val moves = listOf(
        Pair(1, 1) to PlayerSymbol.CROSS,
        Pair(1, 2) to PlayerSymbol.CIRCLE,
        Pair(0, 0) to PlayerSymbol.CROSS,
        Pair(2, 0) to PlayerSymbol.CIRCLE,
        Pair(2, 2) to PlayerSymbol.CROSS,
    )

    val currentBoard = Array(3) { Array(3) { PlayerSymbol.NONE } }

    for ((position, symbole) in moves) {
        val (row, col) = position

        // Add symbol in grid
        currentBoard[row][col] = symbole
        gameBoard(currentBoard.map { it.clone() }.toTypedArray())

        // Symbol display animation
        val key = Pair(row, col)
        val animatable = Animatable(0f)
        symbolAnimations[key] = animatable

        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 200,
                easing = EaseOutBack
            )
        )

        delay(animationSpeed / 2) // shots delay
    }

    // Victory animation
    setWinning(true)
    delay(1000)
    onFinished()
}

@Preview(showBackground = true)
@Composable
fun OXOLoadingAnimationPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OXOLoadingAnimation(
            modifier = Modifier.size(250.dp)
        ){}
    }
}
