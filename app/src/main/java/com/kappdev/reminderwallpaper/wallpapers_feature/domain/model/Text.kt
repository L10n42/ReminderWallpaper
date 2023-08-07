package com.kappdev.reminderwallpaper.wallpapers_feature.domain.model

import android.text.Layout
import androidx.compose.ui.graphics.Color

data class Text(
    val text: String,
    val align: Layout.Alignment,
    val position: TextPosition,
    val style: TextStyle,
    val fontSize: Int,
    val background: Color,
    val foreground: Color
) : WallpaperData()