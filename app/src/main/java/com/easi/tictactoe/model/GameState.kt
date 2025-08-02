package com.easi.tictactoe.model


enum class WinDirection { HORIZONTAL, VERTICAL, DIAGONAL_LEFT, DIAGONAL_RIGHT }

data class GameState(
    val gameConfiguration: GameConfiguration = GameConfiguration(),
    val currentPlayer: Player = gameConfiguration.firstPlayer
)
