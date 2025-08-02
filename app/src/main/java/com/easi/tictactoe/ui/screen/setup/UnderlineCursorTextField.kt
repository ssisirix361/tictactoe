package com.easi.tictactoe.ui.screen.setup

import AudioManager
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easi.tictactoe.ui.theme.bitCountFamily

@Composable
fun UnderlineCursorTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val density = LocalDensity.current
    val context = LocalContext.current

    val infiniteTransition = rememberInfiniteTransition()
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        )
    )

    BasicTextField(
        value = textFieldValue,
        onValueChange = {
            AudioManager.playSound(context = context, sound = SoundType.CLICK)
            textFieldValue = it
            onValueChange(it.text)
        },
        singleLine = true,
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 25.sp,
            textAlign = TextAlign.Start,
            textDecoration = TextDecoration.None,
            fontFamily = bitCountFamily,
        ),
        cursorBrush = SolidColor(Color.Transparent), // On masque le curseur vertical
        onTextLayout = { layoutResult -> textLayoutResult = layoutResult },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) { innerTextField ->
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomStart,
        ) {
            innerTextField()

            val cursorOffsetX = remember(textFieldValue, textLayoutResult) {
                val textLength = textFieldValue.text.length
                val index = textFieldValue.selection.start.coerceIn(0, textLength)

                textLayoutResult?.let {
                    try {
                        when {
                            index == 0 -> 0f
                            else -> {
                                val prevCharRect = it.getCursorRect(index - 1)
                                prevCharRect.right
                            }
                        }
                    } catch (e: IllegalArgumentException) {
                        0f
                    }
                } ?: 0f
            }

            val cursorOffsetDp = remember(cursorOffsetX, density) {
                with(density) { cursorOffsetX.toDp() } + 2.dp
            }

            Box(
                modifier = Modifier
                    .offset(x = cursorOffsetDp)
                    .width(10.dp)
                    .height(2.dp)
                    .alpha(cursorAlpha)
                    .background(Color.White)
            )
        }
    }
}