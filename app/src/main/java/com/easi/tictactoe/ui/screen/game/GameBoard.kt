package com.easi.tictactoe.ui.screen.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.easi.tictactoe.model.GridSize
import com.easi.tictactoe.model.PlayerSymbol
import com.easi.tictactoe.model.WinDirection


@Composable
fun GameBoard(
    gridFormat: GridSize,
    currentBoard: List<List<PlayerSymbol>>,
    currentWinningCell: List<Pair<Int, Int>>,
    winDirection: WinDirection?,
    onCellClick: (Int, Int) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            repeat(gridFormat.value) { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    repeat(gridFormat.value) { col ->
                        GameCell(
                            playerSymbol = currentBoard[row][col],
                            isWinning = currentWinningCell.contains(Pair(row, col)),
                            winDirection = winDirection,
                            modifier = Modifier.drawBehind {
                                val strokeWidth = gridFormat.value.dp.toPx()
                                val rightX = size.width - strokeWidth / 2
                                val bottomY = size.height - strokeWidth / 2
                                val color = Color.White

                                if (col != gridFormat.value - 1) {
                                    drawLine(
                                        color = Color.White,
                                        start = Offset(x = rightX, y = 0f),
                                        end = Offset(x = rightX, y = size.height),
                                        strokeWidth = strokeWidth
                                    )
                                }

                                if (row != gridFormat.value - 1) {
                                    drawLine(
                                        color = color,
                                        start = Offset(x = 0f, y = bottomY),
                                        end = Offset(x = size.width, y = bottomY),
                                        strokeWidth = strokeWidth
                                    )
                                }

                            },
                            onClick = { onCellClick(row, col) }
                        )
                    }
                }
            }
        }
    }
}
