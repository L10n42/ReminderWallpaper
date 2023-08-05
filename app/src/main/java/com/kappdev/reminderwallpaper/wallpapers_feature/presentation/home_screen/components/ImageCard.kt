package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.home_screen.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappdev.reminderwallpaper.core.common.components.DefaultImage

@Composable
fun ImageCard(
    image: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(4.dp)
    ) {
        DefaultImage(
            model = image,
            modifier = Modifier.fillMaxSize()
        )
    }
}