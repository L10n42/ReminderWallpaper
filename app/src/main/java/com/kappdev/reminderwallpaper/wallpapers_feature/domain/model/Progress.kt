package com.kappdev.reminderwallpaper.wallpapers_feature.domain.model

import androidx.compose.ui.graphics.Color

data class Progress(
    val goal: String,
    val complete: Float,
    val background: Color,
    val textColor: Color,
    val chartColor: Color
) : WallpaperData()