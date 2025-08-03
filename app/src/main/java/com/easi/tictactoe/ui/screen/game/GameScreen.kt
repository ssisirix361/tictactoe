package com.example.tictactoe

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.easi.tictactoe.R
import com.easi.tictactoe.model.Player
import com.easi.tictactoe.ui.components.GameButton
import com.easi.tictactoe.ui.components.GradientText
import com.easi.tictactoe.ui.navigation.NavRoutes
import com.easi.tictactoe.ui.screen.TicTacToeBoard.GameBoard
import com.easi.tictactoe.viewmodel.GameViewModel


@Composable
fun MatchScreen(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel(),
    navController: NavController?
) {
    val context = LocalContext.current

    if (viewModel.getCurrentPlayer().isAI) {
        viewModel.playAIMove()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .weight(1F),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.weight(1F), contentAlignment = Alignment.Center ) {
                GradientText(text = stringResource(id = R.string.app_name), fontSize = 50.sp)
            }

            Box(modifier = Modifier.weight(1F), contentAlignment = Alignment.Center ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "TicTacToe Logo",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            Box( modifier= Modifier.padding(20.dp) ) {
                // Current Player Indicator
                CurrentPlayerIndicator(
                    currentPlayer = viewModel.getCurrentPlayer(),
                    isGameOver = viewModel.isGameOver(),
                    winner = viewModel.getWinner()
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Contenu centré verticalement ET horizontalement
                GameBoard(
                    gridFormat = viewModel.getGameConfiguration().gridSize,
                    currentBoard = viewModel.getCurrentBoard(),
                    currentWinningCell = viewModel.getWinningCell(),
                    winDirection = viewModel.winDirection(),
                    onCellClick = { row, col ->
                        if (!viewModel.getCurrentPlayer().isAI) {
                            viewModel.onCellClicked(row = row, col = col)
                        }
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                AnimatedVisibility(
                    visible = viewModel.isGameOver(),
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    GameButton(text = stringResource(R.string.match_new_game_button).uppercase()) {
                        navController?.let {
                            viewModel.resetAndGoBackToSetup()
                            it.navigate(NavRoutes.GAME_SETUP)
                        }
                    }
                }

                AnimatedVisibility(
                    visible = viewModel.isGameOver(),
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    GameButton(text = stringResource(R.string.match_replay_button).uppercase()) {
                        viewModel.resetGame()
                    }
                }
            }
        }

    }
}


@Composable
fun CurrentPlayerIndicator(
    currentPlayer: Player,
    isGameOver: Boolean,
    winner: Player?
) {
    val context = LocalContext.current
    val text = when {
        isGameOver && winner != null -> "🎉 ${winner.name} a gagné !"
        isGameOver -> stringResource(R.string.match_replay_draw_result)
        else -> "Au tour de ${currentPlayer.name} ! "
    }

    if (winner != null) {
        val mediaPlayer = if (winner.isAI) {
            MediaPlayer.create(context, R.raw.defeat_sound)
        } else {
            MediaPlayer.create(context, R.raw.victory_sound)
        }
        mediaPlayer.start()
    }

    AnimatedContent(
        targetState = text,
    ) { targetText ->
        Card(
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = targetText,
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = winner?.symbol?.color ?: currentPlayer.symbol.color,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MatchPreview() {

    MaterialTheme {
        MatchScreen(navController = null)
    }
}