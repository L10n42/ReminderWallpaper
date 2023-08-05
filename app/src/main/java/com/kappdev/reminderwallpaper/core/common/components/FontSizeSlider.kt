package com.kappdev.reminderwallpaper.core.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun FontSizeSlider(
    fontSize: Int,
    modifier: Modifier = Modifier,
    range: ClosedFloatingPointRange<Float> = 10f..24f,
    steps: (ClosedFloatingPointRange<Float>) -> Int = {
        (it.endInclusive - it.start - 2f).toInt()
    },
    onSelect: (Int) -> Unit
) {
    val (value, setValue) = remember { mutableFloatStateOf(fontSize.toFloat()) }

    LaunchedEffect(fontSize) {
        setValue(fontSize.toFloat())
    }

    Column(modifier = modifier) {
        Text(
            text = buildTitle(value.roundToInt()),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        VerticalSpace(8.dp)
        Slider(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = setValue,
            valueRange = range,
            steps = steps(range),
            onValueChangeFinished = {
                onSelect(value.roundToInt())
            }
        )
    }
}

private fun buildTitle(value: Int) = buildAnnotatedString {
    append("Text size: ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
        append(value.toString())
    }
}
