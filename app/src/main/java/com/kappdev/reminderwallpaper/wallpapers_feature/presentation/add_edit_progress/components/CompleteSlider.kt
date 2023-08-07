package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_progress.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.common.components.Keyboard
import com.kappdev.reminderwallpaper.core.common.components.KeyboardLaunchedEffect
import com.kappdev.reminderwallpaper.core.common.components.VerticalSpace
import com.kappdev.reminderwallpaper.core.util.showToast

@Composable
fun CompleteSlider(
    complete: Float,
    modifier: Modifier = Modifier,
    onCompleteChange: (Float) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    val (value, setValue) = remember { mutableFloatStateOf(0f) }
    val (textValue, setTextValue) = remember { mutableStateOf("") }

    fun updateText() {
        setTextValue((value * 100).toInt().toString())
    }

    fun validAndApplyInput() {
        if (isValidInput(textValue)) {
            onCompleteChange(textValue.toFloat() / 100)
            focusManager.clearFocus()
        } else {
            updateText()
            focusManager.clearFocus()
            context.showToast(R.string.wrong_percent_value)
        }
    }

    LaunchedEffect(value) { updateText() }

    KeyboardLaunchedEffect { state ->
        if (state == Keyboard.Closed && isFocused) {
            validAndApplyInput()
        }
    }

    LaunchedEffect(complete) {
        setValue(complete)
    }

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.complete_title),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            BasicTextField(
                value = textValue,
                maxLines = 1,
                modifier = Modifier
                    .width(32.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions {
                    validAndApplyInput()
                },
                onValueChange = { newValue ->
                    setTextValue(newValue)
                },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )

            Text(
                text = "%",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        VerticalSpace(8.dp)
        Slider(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = setValue,
            onValueChangeFinished = {
                onCompleteChange(value)
            }
        )
    }
}

private fun isValidInput(input: String): Boolean {
    return try {
        val intValue = input.toInt()
        intValue in 0..100
    } catch (e: NumberFormatException) {
        false
    }
}