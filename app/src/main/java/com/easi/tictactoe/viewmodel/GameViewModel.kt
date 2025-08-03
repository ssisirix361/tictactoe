package com.easi.tictactoe.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easi.tictactoe.logic.GameLogic.checkWinner
import com.easi.tictactoe.logic.GameLogic.findRandomFreeCell
import com.easi.tictactoe.logic.GameLogic.getComputerBestMove
import com.easi.tictactoe.logic.GameLogic.getWinningCells
import com.easi.tictactoe.logic.GameLogic.isBoardFull
import com.easi.tictactoe.model.GameConfiguration
import com.easi.tictactoe.model.GameMode
import com.easi.tictactoe.model.GameState
import com.easi.tictactoe.model.Level
import com.easi.tictactoe.model.Player
import com.easi.tictactoe.model.PlayerSymbol
import com.easi.tictactoe.model.WinDirection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

        _state.value = _state.value.copy(gameConfiguration = newConfiguration, currentPlayer = newConfiguration.firstPlayer, board = newBoard)
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

            val (winner, direction) = checkWinner(board = newBoard,
                firstPlayer = state.value.gameConfiguration.firstPlayer,
                secondPlayer = state.value.gameConfiguration.secondPlayer,
                needed = state.value.numberCellToWin)

            val winningCells = if (winner != null) {
                getWinningCells(board = newBoard, numberCellToWin = state.value.numberCellToWin)
            } else emptyList()

            _state.value = _state.value.copy(
                board = newBoard,
                currentPlayer = if (currentPlayer.symbol == firstPlayer.symbol) secondPlayer else firstPlayer,
                winner = winner,
                isGameOver = winner != null || isBoardFull(newBoard),
                winningCells = winningCells,
                winDirection = direction
            )
        }
    }

    fun isGameOver(): Boolean {
        return _state.value.isGameOver
    }

    fun winDirection(): WinDirection? {
        return _state.value.winDirection
    }

    fun playAIMove() {
        viewModelScope.launch {
            delay(1000L)

            if (_state.value.gameConfiguration.level == Level.EASY) {
                findRandomFreeCell(board = state.value.board)?.let {
                    onCellClicked(it.first, it.second)

                }
            } else {
                getComputerBestMove(
                    board = state.value.board, firstPlayer = state.value.gameConfiguration.firstPlayer,
                    secondPlayer = state.value.gameConfiguration.secondPlayer, numberCellToWin = state.value.numberCellToWin
                )?.let {
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