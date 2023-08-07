package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common

import androidx.compose.ui.graphics.vector.ImageVector

data class IconButton(
    val icon: ImageVector,
    val descriptionRes: Int,
    val onClick: () -> Unit,
)
