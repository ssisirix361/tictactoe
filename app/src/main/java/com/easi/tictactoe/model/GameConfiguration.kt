package com.easi.tictactoe.model

enum class GameMode(val label: String) {
    SINGLE("Solo"),
    MULTI("Multijoueur");

    companion object {
        fun fromLabel(label: String): GameMode? {
            return values().firstOrNull { it.label == label }
        }
    }
}

enum class GridSize(val value: Int){
    THREE_X_THREE(value = 3),
    FOUR_X_FOUR(value = 4)
}

enum class Level {
    EASY,
    DIFFICULT
}

data class GameConfiguration(
    var firstPlayer: Player = Player(symbol = PlayerSymbol.CROSS),
    var secondPlayer: Player = Player(symbol = PlayerSymbol.CIRCLE),
    var mode: GameMode = GameMode.SINGLE,
    var level: Level = Level.EASY,
    var gridSize: GridSize = GridSize.THREE_X_THREE
)