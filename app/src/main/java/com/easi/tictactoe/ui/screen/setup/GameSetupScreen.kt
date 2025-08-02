package com.easi.tictactoe.ui.screen.setup

import AudioManager
import android.content.Context
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.easi.tictactoe.R
import com.easi.tictactoe.components.AnimatedText
import com.easi.tictactoe.components.GameButton
import com.easi.tictactoe.components.GradientText
import com.easi.tictactoe.model.GameConfiguration
import com.easi.tictactoe.model.GameMode
import com.easi.tictactoe.model.GridSize
import com.easi.tictactoe.model.Level
import com.easi.tictactoe.ui.theme.TictactoeTheme
import com.easi.tictactoe.viewmodel.GameViewModel

enum class GameSetupStep {
    MODE_SELECTION,
    PLAYER_CONFIGURATION
}

@Composable
fun GameSetupScreen(modifier: Modifier = Modifier, viewModel: GameViewModel = viewModel(), navController: NavController?) {
    val context = LocalContext.current
    AudioManager.initBackgroundMusic(context = context)
    AudioManager.playBackgroundMusic()

    SetupBody(modifier = modifier, navController = navController, viewModel = viewModel, context = context)
}

@Composable
fun SetupBody(modifier: Modifier = Modifier, viewModel: GameViewModel = GameViewModel(), context: Context, navController: NavController? = null) {
    var currentStep by remember { mutableStateOf(GameSetupStep.MODE_SELECTION) }
    var gameConfiguration by remember { mutableStateOf(viewModel.getGameConfiguration()) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Crossfade(targetState = currentStep) { step ->
            when (step) {
                GameSetupStep.MODE_SELECTION -> {
                    ModeSelectionView(context = context, selectedMode =  gameConfiguration.mode) {
                        gameConfiguration.mode = it
                        currentStep = GameSetupStep.PLAYER_CONFIGURATION
                    }
                }

                GameSetupStep.PLAYER_CONFIGURATION -> {
                    PlayerConfiguration(
                        context = context,
                        gameConfiguration = gameConfiguration.copy(
                            firstPlayer = gameConfiguration.firstPlayer.copy(),
                            secondPlayer = gameConfiguration.secondPlayer.copy(),
                            level = gameConfiguration.level,
                            mode = gameConfiguration.mode,
                            gridSize = gameConfiguration.gridSize
                        ),
                        onUpdate = {
                            gameConfiguration = it
                        }, onNext = {
                            viewModel.setGameConfiguration(gameConfiguration)
                        }, onGoBack = {
                            gameConfiguration = viewModel.getGameConfiguration()
                            currentStep = GameSetupStep.MODE_SELECTION
                        })
                }
            }
        }
    }
}

@Composable
fun ModeSelectionView(context: Context, selectedMode: GameMode, onModeSelected: (GameMode) -> Unit) {
    var selected by remember { mutableStateOf(selectedMode) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedText(
                    fullText = stringResource(R.string.game_setup_choose_mode_title),
                    fontSize = 35.sp
                )

                Spacer(modifier = Modifier.height(150.dp))

                GameModeSelector(
                    context = context,
                    selectedMode = selected,
                    onModeSelected = { selected = it }
                )
            }
        }


        GameButton(modifier = Modifier.align(Alignment.CenterHorizontally), text = stringResource(R.string.game_setup_next_button)) {
            onModeSelected(selected)
        }
    }
}


@Composable
fun PlayerConfiguration(context: Context, gameConfiguration: GameConfiguration, onUpdate: (GameConfiguration) -> Unit, onNext: () -> Unit, onGoBack: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.weight(1F)) {
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AnimatedText(
                    fullText = stringResource(R.string.game_setup_choose_pseudo_title),
                    delayMillis = 25L,
                    fontSize = 35.sp
                )
            }
            Box(
                modifier = Modifier
                    .weight(1.3f),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()) {
                        GradientText(
                            modifier = Modifier
                                .padding(start = 20.dp),
                            text = stringResource(R.string.game_setup_choose_grid_format)
                        )

                        Row(
                            modifier = Modifier
                                .weight(1F)
                                .clickable {
                                    AudioManager.playSound(context = context, sound = SoundType.CLICK)
                                    gameConfiguration.gridSize = GridSize.THREE_X_THREE
                                    onUpdate(gameConfiguration)
                                },
                            horizontalArrangement = Arrangement.End
                        ) {
                            if (gameConfiguration.gridSize == GridSize.THREE_X_THREE) {
                                ArrowIcon()
                            } else {
                                Spacer(modifier = Modifier.size(24.dp)) // réserve le même espace
                            }
                            GradientText(text = stringResource(R.string.game_setup_choose_grid_format_3x3).uppercase())
                        }

                        Row(
                            modifier = Modifier
                                .weight(1F)
                                .clickable {
                                    AudioManager.playSound(context = context, sound = SoundType.CLICK)
                                    gameConfiguration.gridSize = GridSize.FOUR_X_FOUR
                                    onUpdate(gameConfiguration)
                                },
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (gameConfiguration.gridSize == GridSize.FOUR_X_FOUR) {
                                ArrowIcon()
                            } else {
                                Spacer(modifier = Modifier.size(24.dp)) // réserve le même espace
                            }
                            GradientText(text = stringResource(R.string.game_setup_choose_grid_format_4x4).uppercase())
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    if (gameConfiguration.mode == GameMode.SINGLE) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            GradientText(
                                modifier = Modifier.padding(start = 20.dp),
                                text = stringResource(R.string.game_setup_choose_level).uppercase()
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            AudioManager.playSound(context = context, sound = SoundType.CLICK)
                                            gameConfiguration.level = Level.EASY
                                            onUpdate(gameConfiguration)
                                        },
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (gameConfiguration.level == Level.EASY) {
                                        ArrowIcon()
                                    } else {
                                        Spacer(modifier = Modifier.size(24.dp))
                                    }
                                    GradientText(text = stringResource(R.string.game_setup_choose_easy_game).uppercase())
                                }

                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            AudioManager.playSound(context = context, sound = SoundType.CLICK)
                                            gameConfiguration.level = Level.DIFFICULT
                                            onUpdate(gameConfiguration)
                                        },
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (gameConfiguration.level == Level.DIFFICULT) {
                                        ArrowIcon()
                                    } else {
                                        Spacer(modifier = Modifier.size(24.dp))
                                    }
                                    GradientText(text = stringResource(R.string.game_setup_choose_difficult_game).uppercase())
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    PlayerInputField(
                        modifier = Modifier.padding(start = 20.dp),
                        label = if (gameConfiguration.mode == GameMode.SINGLE) stringResource(R.string.game_setup_choose_player) else stringResource(R.string.game_setup_choose_player_one),
                        playerName = gameConfiguration.firstPlayer.name,
                        onValueChange = {
                            gameConfiguration.firstPlayer.name = it
                            onUpdate(gameConfiguration)
                        }
                    )

                    SymbolSelector(
                        modifier = Modifier.fillMaxWidth(),
                        context = context,
                        selectedSymbol = gameConfiguration.firstPlayer.symbol
                    ) {
                        if (gameConfiguration.firstPlayer.symbol != it) {
                            Log.d("SymbolSelector", "Clicked symbol: $it")

                            gameConfiguration.secondPlayer.symbol = gameConfiguration.firstPlayer.symbol
                            gameConfiguration.firstPlayer.symbol = it
                            onUpdate(gameConfiguration)
                        }
                    }

                    if (gameConfiguration.mode == GameMode.MULTI) {
                        PlayerInputField(
                            modifier = Modifier.padding(start = 20.dp),
                            label = stringResource(R.string.game_setup_choose_player_two),
                            playerName = gameConfiguration.secondPlayer.name,
                            onValueChange = {
                                gameConfiguration.secondPlayer.name = it
                                onUpdate(gameConfiguration)
                            }
                        )
                        SymbolSelector(Modifier.fillMaxWidth(),
                            context= context,
                            selectedSymbol = gameConfiguration.secondPlayer.symbol) {
                            if (gameConfiguration.secondPlayer.symbol != it) {
                                gameConfiguration.firstPlayer.symbol = gameConfiguration.secondPlayer.symbol
                                gameConfiguration.secondPlayer.symbol = it
                                onUpdate(gameConfiguration)
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            GameButton(text = stringResource(R.string.game_setup_back_button)) {
                onGoBack()
            }

            GameButton(text = stringResource(R.string.game_setup_next_button)) {
                onNext()
            }
        }
    }
}

@Composable
fun ArrowIcon() {
    Icon(
        imageVector = Icons.Default.PlayArrow,
        contentDescription = "Flèche",
        modifier = Modifier
            .size(24.dp),
        tint = MaterialTheme.colorScheme.secondary
    )
}

@Preview(showBackground = true)
@Composable
fun SetupPreview() {
    TictactoeTheme {
        SetupBody(context = LocalContext.current)
    }
}


