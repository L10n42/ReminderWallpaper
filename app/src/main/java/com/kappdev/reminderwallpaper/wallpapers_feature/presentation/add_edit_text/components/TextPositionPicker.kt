package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_text.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlignVerticalBottom
import androidx.compose.material.icons.rounded.AlignVerticalCenter
import androidx.compose.material.icons.rounded.AlignVerticalTop
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.TextPosition

@Composable
fun TextPositionPicker(
    selected: TextPosition,
    modifier: Modifier = Modifier,
    onSelect: (TextPosition) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextPosition.values().forEach { align ->
            IconButton(
                onClick = {
                    onSelect(align)
                }
            ) {
                Icon(
                    imageVector = when (align) {
                        TextPosition.TOP -> Icons.Rounded.AlignVerticalTop
                        TextPosition.CENTER -> Icons.Rounded.AlignVerticalCenter
                        TextPosition.BOTTOM -> Icons.Rounded.AlignVerticalBottom
                    },
                    contentDescription = align.name,
                    tint = if (selected == align) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            }
        }
    }
}