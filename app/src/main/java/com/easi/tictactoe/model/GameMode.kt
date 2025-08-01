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