package com.easi.tictactoe.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.easi.tictactoe.model.GameConfiguration
import com.easi.tictactoe.model.GameMode
import com.easi.tictactoe.model.GameState
import com.easi.tictactoe.model.Player
import com.easi.tictactoe.model.PlayerSymbol


class GameViewModel : ViewModel() {

    private var _state = mutableStateOf(GameState())
    private val state: State<GameState> = _state

    fun getGameConfiguration(): GameConfiguration {
        return state.value.gameConfiguration
    }

    fun setGameConfiguration(newConfiguration: GameConfiguration) {
        newConfiguration.secondPlayer =  if (newConfiguration.mode == GameMode.SINGLE) {
            Player("AI", symbol = newConfiguration.secondPlayer.symbol, isAI = true)
        } else {
            Player(name = newConfiguration.secondPlayer.name, symbol = newConfiguration.secondPlayer.symbol, isAI = false)
        }
        val newBoard = List(newConfiguration.gridSize.value) { List(newConfiguration.gridSize.value) { PlayerSymbol.NONE } }

        _state.value = _state.value.copy(gameConfiguration = newConfiguration, currentPlayer = newConfiguration.firstPlayer)
    }

}