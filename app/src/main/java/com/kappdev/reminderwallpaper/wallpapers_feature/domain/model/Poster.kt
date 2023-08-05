package com.kappdev.reminderwallpaper.wallpapers_feature.domain.model

import android.net.Uri
import android.text.Layout
import androidx.compose.ui.graphics.Color

data class Poster(
    val text: String,
    val align: Layout.Alignment,
    val style: TextStyle,
    val fontSize: Int,
    val background: Color,
    val foreground: Color,
    val image: Uri
)
