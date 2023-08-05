package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.components

import android.text.Layout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatAlignCenter
import androidx.compose.material.icons.rounded.FormatAlignLeft
import androidx.compose.material.icons.rounded.FormatAlignRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TextAlignmentPicker(
    selected: Layout.Alignment,
    modifier: Modifier = Modifier,
    onSelect: (Layout.Alignment) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AlignmentValues.forEach { align ->
            IconButton(
                onClick = {
                    onSelect(align)
                }
            ) {
                Icon(
                    imageVector = when (align) {
                        Layout.Alignment.ALIGN_NORMAL -> Icons.Rounded.FormatAlignLeft
                        Layout.Alignment.ALIGN_CENTER -> Icons.Rounded.FormatAlignCenter
                        Layout.Alignment.ALIGN_OPPOSITE -> Icons.Rounded.FormatAlignRight
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

private val AlignmentValues = arrayOf(
    Layout.Alignment.ALIGN_NORMAL,
    Layout.Alignment.ALIGN_CENTER,
    Layout.Alignment.ALIGN_OPPOSITE,
)
