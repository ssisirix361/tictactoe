package com.easi.tictactoe.logic

import com.easi.tictactoe.model.Player
import com.easi.tictactoe.model.PlayerSymbol
import com.easi.tictactoe.model.WinDirection


object GameLogic {

    fun isBoardFull(board: List<List<PlayerSymbol>>): Boolean {
        return board.all { row -> row.all { it != PlayerSymbol.NONE } }
    }

     fun checkWinner(
        board: List<List<PlayerSymbol>>,
        firstPlayer: Player,
        secondPlayer: Player,
        needed: Int
    ): Pair<Player?, WinDirection?> {
        val players = listOf(firstPlayer, secondPlayer)
        val size = board.size

        for (player in players) {
            val symbol = player.symbol

            // Line
            for (row in 0 until size) {
                for (startCol in 0..size - needed) {
                    if ((0 until needed).all { offset -> board[row][startCol + offset] == symbol }) {
                        return Pair(player, WinDirection.HORIZONTAL)
                    }
                }
            }

            // Row
            for (col in 0 until size) {
                for (startRow in 0..size - needed) {
                    if ((0 until needed).all { offset -> board[startRow + offset][col] == symbol }) {
                        return Pair(player, WinDirection.VERTICAL)
                    }
                }
            }

            // Diagonal ↘
            for (row in 0..size - needed) {
                for (col in 0..size - needed) {
                    if ((0 until needed).all { offset -> board[row + offset][col + offset] == symbol }) {
                        return Pair(player, WinDirection.DIAGONAL_LEFT)
                    }
                }
            }

            // Diagonal ↙
            for (row in 0..size - needed) {
                for (col in needed - 1 until size) {
                    if ((0 until needed).all { offset -> board[row + offset][col - offset] == symbol }) {
                        return Pair(player, WinDirection.DIAGONAL_RIGHT)
                    }
                }
            }
        }

        return Pair(null, null)
    }

    fun getWinningCells(board: List<List<PlayerSymbol>>, numberCellToWin: Int): List<Pair<Int, Int>> {
        val size = board.size

        // Lines
        for (row in 0 until size) {
            for (startCol in 0..size - numberCellToWin) {
                if ((0 until numberCellToWin).all { offset ->
                        board[row][startCol + offset] != PlayerSymbol.NONE &&
                                board[row][startCol + offset] == board[row][startCol]
                    }) {
                    return (0 until numberCellToWin).map { offset -> Pair(row, startCol + offset) }
                }
            }
        }

        // Column
        for (col in 0 until size) {
            for (startRow in 0..size - numberCellToWin) {
                if ((0 until numberCellToWin).all { offset ->
                        board[startRow + offset][col] != PlayerSymbol.NONE &&
                                board[startRow + offset][col] == board[startRow][col]
                    }) {
                    return (0 until numberCellToWin).map { offset -> Pair(startRow + offset, col) }
                }
            }
        }

        // Diagonal ↘
        for (row in 0..size - numberCellToWin) {
            for (col in 0..size - numberCellToWin) {
                if ((0 until numberCellToWin).all { offset ->
                        board[row + offset][col + offset] != PlayerSymbol.NONE &&
                                board[row + offset][col + offset] == board[row][col]
                    }) {
                    return (0 until numberCellToWin).map { offset -> Pair(row + offset, col + offset) }
                }
            }
        }

        // Diagonal ↙
        for (row in 0..size - numberCellToWin) {
            for (col in numberCellToWin - 1 until size) {
                if ((0 until numberCellToWin).all { offset ->
                        board[row + offset][col - offset] != PlayerSymbol.NONE &&
                                board[row + offset][col - offset] == board[row][col]
                    }) {
                    return (0 until numberCellToWin).map { offset -> Pair(row + offset, col - offset) }
                }
            }
        }

        return emptyList()
    }

    fun findRandomFreeCell(board: List<List<PlayerSymbol>>): Pair<Int, Int>? {
        val freeCells = mutableListOf<Pair<Int, Int>>()
        for (row in board.indices) {
            for (col in board[row].indices) {
                if (board[row][col] == PlayerSymbol.NONE) {
                    freeCells.add(Pair(row, col))
                }
            }
        }
        return freeCells.randomOrNull()
    }

    private fun findBestMove(
        board: List<List<PlayerSymbol>>,
        symbol: PlayerSymbol,
        needed: Int
    ): Pair<Int, Int>? {
        val size = board.size
        for (row in 0 until size) {
            for (col in 0 until size) {
                if (board[row][col] == PlayerSymbol.NONE) {
                    val testBoard = board.map { it.toMutableList() }
                    testBoard[row][col] = symbol
                    val (winner, _) = checkWinner(testBoard,
                        Player("Temp", symbol = symbol),
                        Player("Other", symbol = PlayerSymbol.NONE),
                        needed)
                    if (winner?.symbol == symbol) {
                        return Pair(row, col)
                    }
                }
            }
        }
        return null
    }

    fun getComputerBestMove(
        board: List<List<PlayerSymbol>>,
        firstPlayer: Player,
        secondPlayer: Player,
        numberCellToWin: Int
    ): Pair<Int, Int>? {

        // Attack
        findBestMove(board, secondPlayer.symbol, numberCellToWin)?.let { return it }

        //Defend
        findBestMove(board, firstPlayer.symbol, numberCellToWin)?.let { return it }

        val center = Pair(board.size / 2, board.size / 2)
        if (board[center.first][center.second] == PlayerSymbol.NONE) return center

        return findRandomFreeCell(board)
    }
}
