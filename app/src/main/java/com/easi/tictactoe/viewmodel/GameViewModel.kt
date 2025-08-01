package com.easi.tictactoe.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.easi.tictactoe.model.GameState


class GameViewModel : ViewModel() {

    private var _state = mutableStateOf(GameState())
    private val state: State<GameState> = _state

}