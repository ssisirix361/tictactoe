package com.easi.tictactoe.ui.screen.setup

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.easi.tictactoe.R
import com.easi.tictactoe.components.AnimatedText
import com.easi.tictactoe.model.GameMode
import com.easi.tictactoe.ui.theme.TictactoeTheme
import com.easi.tictactoe.viewmodel.GameViewModel

@Composable
fun GameSetupScreen(modifier: Modifier, viewModel: GameViewModel = viewModel(), navController: NavController?) {
    SetupBody(
        modifier = modifier, navController = navController, viewModel = viewModel,
    )
}

@Composable
fun SetupBody( modifier: Modifier = Modifier, viewModel: GameViewModel = GameViewModel(), navController: NavController? = null
) {
    val context = LocalContext.current
    var modeSelected by remember { mutableStateOf(GameMode.SINGLE) }

    ModeSelectionView(context = context, selectedMode = modeSelected) { modeSelected = it}
}

@Composable
fun ModeSelectionView(context: Context, selectedMode: GameMode, onModeSelected: (GameMode) -> Unit) {
    var selected by remember { mutableStateOf(selectedMode) }

    Column(
        modifier = Modifier.fillMaxSize(),
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
fun GameButton(modifier: Modifier, text: String, content: () -> Unit) {

}


@Preview(showBackground = true)
@Composable
fun SetupPreview() {
    TictactoeTheme {
        SetupBody()
    }
}


