package com.easi.tictactoe.model


enum class WinDirection { HORIZONTAL, VERTICAL, DIAGONAL_LEFT, DIAGONAL_RIGHT }

data class GameState(
    val gameMode: GameMode = GameMode.SINGLE,
)
