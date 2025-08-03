package com.easi.tictactoe.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easi.tictactoe.logic.GameLogic.checkWin
import com.easi.tictactoe.model.GameConfiguration
import com.easi.tictactoe.model.GameMode
import com.easi.tictactoe.model.GameState
import com.easi.tictactoe.model.Level
import com.easi.tictactoe.model.Player
import com.easi.tictactoe.model.PlayerSymbol
import com.easi.tictactoe.model.WinDirection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class GameViewModel : ViewModel() {

    private var _state = mutableStateOf(GameState())
    private val state: State<GameState> = _state

    fun getGameConfiguration(): GameConfiguration {
        return state.value.gameConfiguration
    }

    fun setGameConfiguration(newConfiguration: GameConfiguration) {
        newConfiguration.secondPlayer = if (newConfiguration.mode == GameMode.SINGLE) {
            Player("AI", symbol = newConfiguration.secondPlayer.symbol, isAI = true)
        } else {
            Player(name = newConfiguration.secondPlayer.name, symbol = newConfiguration.secondPlayer.symbol, isAI = false)
        }
        val newBoard = List(newConfiguration.gridSize.value) { List(newConfiguration.gridSize.value) { PlayerSymbol.NONE } }

        _state.value = _state.value.copy(gameConfiguration = newConfiguration, currentPlayer = newConfiguration.firstPlayer)
    }

    fun getCurrentPlayer(): Player {
        return state.value.currentPlayer
    }

    fun getCurrentBoard(): List<List<PlayerSymbol>> {
        return _state.value.board
    }


    fun getWinner(): Player? {
        return _state.value.winner
    }

    fun getWinningCell(): List<Pair<Int, Int>> {
        return _state.value.winningCells
    }

    fun onCellClicked(row: Int, col: Int) {
        val currentBoard = _state.value.board
        val currentPlayer = _state.value.currentPlayer
        val firstPlayer = _state.value.gameConfiguration.firstPlayer
        val secondPlayer = _state.value.gameConfiguration.secondPlayer

        if (currentBoard[row][col] == PlayerSymbol.NONE && !_state.value.isGameOver) {
            val newBoard = currentBoard.mapIndexed { r, rowList ->
                rowList.mapIndexed { c, cell ->
                    if (r == row && c == col) currentPlayer.symbol else cell
                }
            }

            val winner = checkWinner(newBoard)
            val winningCells = if (winner != null) {
                getWinningCells(newBoard)
            } else emptyList()

            _state.value = _state.value.copy(
                board = newBoard,
                currentPlayer = if (currentPlayer.symbol == firstPlayer.symbol) secondPlayer else firstPlayer,
                winner = winner,
                isGameOver = winner != null || isBoardFull(newBoard),
                winningCells = winningCells,
                winDirection = winDirection()
            )
        }
    }

    private fun checkWinner(board: List<List<PlayerSymbol>>): Player? {
        val firstPlayer = _state.value.gameConfiguration.firstPlayer
        val secondPlayer = _state.value.gameConfiguration.secondPlayer

        val players = listOf(firstPlayer, secondPlayer)
        val size = board.size
        val needed = _state.value.numberCellToWin // nombre de symboles nécessaires pour gagner

        for (player in players) {

            // Vérifier les lignes
            for (row in 0 until size) {
                for (startCol in 0..size - needed) {
                    if ((0 until needed).all { offset -> board[row][startCol + offset] == player.symbol }) {
                        _state.value = _state.value.copy(winDirection = WinDirection.HORIZONTAL)
                        return player
                    }
                }
            }

            // Vérifier les colonnes
            for (col in 0 until size) {
                for (startRow in 0..size - needed) {
                    if ((0 until needed).all { offset -> board[startRow + offset][col] == player.symbol }) {
                        _state.value = _state.value.copy(winDirection = WinDirection.VERTICAL)
                        return player
                    }
                }
            }

            // Vérifier diagonale gauche->droite
            for (row in 0..size - needed) {
                for (col in 0..size - needed) {
                    if ((0 until needed).all { offset -> board[row + offset][col + offset] == player.symbol }) {
                        _state.value = _state.value.copy(winDirection = WinDirection.DIAGONAL_LEFT)
                        return player
                    }
                }
            }

            // Vérifier diagonale droite->gauche
            for (row in 0..size - needed) {
                for (col in needed - 1 until size) {
                    if ((0 until needed).all { offset -> board[row + offset][col - offset] == player.symbol }) {
                        _state.value = _state.value.copy(winDirection = WinDirection.DIAGONAL_RIGHT)
                        return player
                    }
                }
            }
        }

        return null
    }


    private fun getWinningCells(board: List<List<PlayerSymbol>>): List<Pair<Int, Int>> {
        val size = _state.value.gameConfiguration.gridSize.value
        val needed = _state.value.numberCellToWin

        // Vérifier les lignes
        for (row in 0 until size) {
            for (startCol in 0..size - needed) {
                if ((0 until needed).all { offset ->
                        board[row][startCol + offset] != PlayerSymbol.NONE &&
                                board[row][startCol + offset] == board[row][startCol]
                    }) {
                    return (0 until needed).map { offset -> Pair(row, startCol + offset) }
                }
            }
        }

        // Vérifier les colonnes
        for (col in 0 until size) {
            for (startRow in 0..size - needed) {
                if ((0 until needed).all { offset ->
                        board[startRow + offset][col] != PlayerSymbol.NONE &&
                                board[startRow + offset][col] == board[startRow][col]
                    }) {
                    return (0 until needed).map { offset -> Pair(startRow + offset, col) }
                }
            }
        }

        // Vérifier diagonale gauche -> droite
        for (row in 0..size - needed) {
            for (col in 0..size - needed) {
                if ((0 until needed).all { offset ->
                        board[row + offset][col + offset] != PlayerSymbol.NONE &&
                                board[row + offset][col + offset] == board[row][col]
                    }) {
                    return (0 until needed).map { offset -> Pair(row + offset, col + offset) }
                }
            }
        }

        // Vérifier diagonale droite -> gauche
        for (row in 0..size - needed) {
            for (col in needed - 1 until size) {
                if ((0 until needed).all { offset ->
                        board[row + offset][col - offset] != PlayerSymbol.NONE &&
                                board[row + offset][col - offset] == board[row][col]
                    }) {
                    return (0 until needed).map { offset -> Pair(row + offset, col - offset) }
                }
            }
        }

        return emptyList()
    }


    fun isGameOver(): Boolean {
        return _state.value.isGameOver
    }

    fun winDirection(): WinDirection? {
        return _state.value.winDirection
    }

    private fun isBoardFull(board: List<List<PlayerSymbol>>): Boolean {
        return board.all { row -> row.all { it != PlayerSymbol.NONE } }
    }

    private fun findRandomFreeCell(): Pair<Int, Int>? {
        val freeCells = mutableListOf<Pair<Int, Int>>()
        val currentBoard = _state.value.board
        for (rowIndex in currentBoard.indices) {
            for (colIndex in currentBoard[rowIndex].indices) {
                if (currentBoard[rowIndex][colIndex] == PlayerSymbol.NONE) {
                    freeCells.add(Pair(rowIndex, colIndex))
                }
            }
        }
        return if (freeCells.isNotEmpty()) {
            freeCells[Random.nextInt(freeCells.size)]
        } else null
    }

    private fun getComputerBestMove(): Pair<Int, Int>? {
        val current = _state.value
        val board = _state.value.board
        val firstPlayer = current.gameConfiguration.firstPlayer
        val secondPlayer = current.gameConfiguration.secondPlayer

        findBestMove(firstPlayer.symbol)?.let { return it }

        // 2️⃣ Sinon, bloque un coup gagnant du joueur
        findBestMove(secondPlayer.symbol)?.let { return it }

        // 3️⃣ Sinon, joue aléatoirement ou sur le centre
        val center = Pair(1, 1)
        if (board[1][1] == PlayerSymbol.NONE) return center

        val emptyCells = board.flatMapIndexed { row, rowList ->
            rowList.mapIndexedNotNull { col, cell ->
                if (cell == PlayerSymbol.NONE) Pair(row, col) else null
            }
        }
        return emptyCells.randomOrNull()
    }

    private fun findBestMove(symbol: PlayerSymbol): Pair<Int, Int>? {
        val board = _state.value.board
        val size = board.size
        val needed = 3

        for (row in 0 until size) {
            for (col in 0 until size) {
                if (board[row][col] == PlayerSymbol.NONE) {
                    val testBoard = board.map { it.toMutableList() }
                    testBoard[row][col] = symbol
                    if (checkWin(testBoard, symbol, needed)) return Pair(row, col)
                }
            }
        }
        return null
    }


    fun playAIMove() {
        viewModelScope.launch {
            delay(1000L)

            if (_state.value.gameConfiguration.level == Level.EASY) {
                findRandomFreeCell()?.let {
                    onCellClicked(it.first, it.second)
                }
            } else {
                getComputerBestMove()?.let {
                    onCellClicked(it.first, it.second)
                }
            }
        }
    }

    fun resetGame() {
        _state.value = GameState(
            gameConfiguration = _state.value.gameConfiguration
        )
    }

    fun resetAndGoBackToSetup() {
        _state.value = GameState()
    }


}