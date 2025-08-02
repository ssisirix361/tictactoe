package com.easi.tictactoe.ui.screen.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easi.tictactoe.R
import com.easi.tictactoe.components.GradientText


@Composable
fun PlayerInputField(
    modifier: Modifier,
    label: String = stringResource(R.string.game_setup_choose_player_one),
    playerName: String,
    onValueChange: (String) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GradientText(text = label)

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Flèche",
                modifier = Modifier
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.secondary
            )

            UnderlineCursorTextField(value = playerName) { onValueChange(it) }
        }
    }
}