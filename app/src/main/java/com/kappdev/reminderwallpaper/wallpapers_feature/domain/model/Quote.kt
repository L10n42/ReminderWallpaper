package com.kappdev.reminderwallpaper.wallpapers_feature.domain.model

import androidx.compose.ui.graphics.Color

data class Quote(
    val quote: String,
    val author: String,
    val fontSize: Int,
    val background: Color,
    val foreground: Color
) : WallpaperData()
