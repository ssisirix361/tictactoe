package com.easi.tictactoe.logic

import com.easi.tictactoe.model.PlayerSymbol


object GameLogic {

    fun checkWin(board: List<List<PlayerSymbol>>, symbol: PlayerSymbol, needed: Int = 3): Boolean {
        val size = board.size

        // Lignes
        for (row in 0 until size) {
            for (startCol in 0..size - needed) {
                if ((0 until needed).all { board[row][startCol + it] == symbol }) return true
            }
        }

        // Colonnes
        for (col in 0 until size) {
            for (startRow in 0..size - needed) {
                if ((0 until needed).all { board[startRow + it][col] == symbol }) return true
            }
        }

        // Diagonales ↘
        for (row in 0..size - needed) {
            for (col in 0..size - needed) {
                if ((0 until needed).all { board[row + it][col + it] == symbol }) return true
            }
        }

        // Diagonales ↙
        for (row in 0..size - needed) {
            for (col in needed - 1 until size) {
                if ((0 until needed).all { board[row + it][col - it] == symbol }) return true
            }
        }

        return false
    }


}