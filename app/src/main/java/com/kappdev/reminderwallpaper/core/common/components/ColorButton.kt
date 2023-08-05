package com.kappdev.reminderwallpaper.core.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColorButton(
    title: String,
    modifier: Modifier = Modifier,
    color: Color,
    onClick: () -> Unit
) {
    val boxBorder = Modifier.border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.onBackground,
        shape = BoxShape
    )

    Row(
        modifier = modifier
            .clip(BoxShape)
            .clickable(onClick = onClick)
            .then(boxBorder)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color, CircleShape)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface, shape = CircleShape)
                .clip(CircleShape)
                .clickable(onClick = onClick)
        )
    }
}

private val BoxShape = RoundedCornerShape(4.dp)