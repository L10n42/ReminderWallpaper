package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_progress.components

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
import com.kappdev.reminderwallpaper.core.common.components.VerticalSpace

@Composable
fun CompleteSlider(
    complete: Int,
    modifier: Modifier = Modifier,
    onCompleteChange: (Int) -> Unit
) {
    val (value, setValue) = remember { mutableFloatStateOf(0f) }
    val percent = (value * 100).toInt()

    LaunchedEffect(complete) {
        setValue(complete / 100f)
    }

    Column(modifier = modifier) {
        Text(
            text = buildTitle(percent),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        VerticalSpace(8.dp)
        Slider(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = setValue,
            onValueChangeFinished = {
                onCompleteChange(percent)
            }
        )
    }
}

private fun buildTitle(value: Int) = buildAnnotatedString {
    append("Complete: ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
        append(value.toString())
    }
}