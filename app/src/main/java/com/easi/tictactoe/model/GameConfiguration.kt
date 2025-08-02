package com.easi.tictactoe.model


data class GameConfiguration(
    var firstPlayer: Player = Player(symbol = PlayerSymbol.CROSS),
    var secondPlayer: Player = Player(symbol = PlayerSymbol.CIRCLE),
    var mode: GameMode = GameMode.SINGLE,
    var level: Level = Level.EASY,
    var gridSize: GridSize = GridSize.THREE_X_THREE
)