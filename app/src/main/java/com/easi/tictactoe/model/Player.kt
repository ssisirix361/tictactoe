package com.easi.tictactoe.model


data class Player(
    var name: String = "",
    val isAI: Boolean = false,
    var symbol: PlayerSymbol = PlayerSymbol.CROSS
)
