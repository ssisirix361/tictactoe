package com.easi.tictactoe.model


enum class WinDirection { HORIZONTAL, VERTICAL, DIAGONAL_LEFT, DIAGONAL_RIGHT }

data class GameState(
    val gameConfiguration: GameConfiguration = GameConfiguration(),
    val board: List<List<PlayerSymbol>> = List(gameConfiguration.gridSize.value) { List(gameConfiguration.gridSize.value) { PlayerSymbol.NONE } },
    val currentPlayer: Player = gameConfiguration.firstPlayer,
    val winner: Player? = null,
    val isGameOver: Boolean = false,
    val winningCells: List<Pair<Int, Int>> = emptyList(),
    val winDirection: WinDirection? = null,
    val numberCellToWin: Int = 3
)
