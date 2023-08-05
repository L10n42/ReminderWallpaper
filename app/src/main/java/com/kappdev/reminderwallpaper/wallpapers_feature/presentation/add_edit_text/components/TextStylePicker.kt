package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_text.components

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
            checked = (style is TextStyle.Bold || style is TextStyle.BoldItalic),
            modifier = Modifier.fillMaxWidth(0.5f),
            onCheckedChange = { checked ->
                val newStyle = if (checked) {
                    if (style is TextStyle.Italic) TextStyle.BoldItalic else TextStyle.Bold
                } else {
                    if (style is TextStyle.BoldItalic) TextStyle.Italic else TextStyle.Normal
                }
                onStyleChange(newStyle)
            }
        )

        TitledCheckBox(
            title = stringResource(R.string.italic_title),
            checked = (style is TextStyle.Italic || style is TextStyle.BoldItalic),
            onCheckedChange = { checked ->
                val newStyle = if (checked) {
                    if (style is TextStyle.Bold) TextStyle.BoldItalic else TextStyle.Italic
                } else {
                    if (style is TextStyle.BoldItalic) TextStyle.Bold else TextStyle.Normal
                }
                onStyleChange(newStyle)
            }
        )
    }
}