package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.common.components.TitledCheckBox
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.TextStyle

@Composable
fun TextStylePicker(
    style: TextStyle,
    modifier: Modifier = Modifier,
    onStyleChange: (TextStyle) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        TitledCheckBox(
            title = stringResource(R.string.bold_title),
            checked = (style == TextStyle.BOLD || style == TextStyle.BOLD_ITALIC),
            modifier = Modifier.fillMaxWidth(0.5f),
            onCheckedChange = { checked ->
                val newStyle = if (checked) {
                    if (style == TextStyle.ITALIC) TextStyle.BOLD_ITALIC else TextStyle.BOLD
                } else {
                    if (style == TextStyle.BOLD_ITALIC) TextStyle.ITALIC else TextStyle.NORMAL
                }
                onStyleChange(newStyle)
            }
        )

        TitledCheckBox(
            title = stringResource(R.string.italic_title),
            checked = (style == TextStyle.ITALIC || style == TextStyle.BOLD_ITALIC),
            onCheckedChange = { checked ->
                val newStyle = if (checked) {
                    if (style == TextStyle.BOLD) TextStyle.BOLD_ITALIC else TextStyle.ITALIC
                } else {
                    if (style == TextStyle.BOLD_ITALIC) TextStyle.BOLD else TextStyle.NORMAL
                }
                onStyleChange(newStyle)
            }
        )
    }
}