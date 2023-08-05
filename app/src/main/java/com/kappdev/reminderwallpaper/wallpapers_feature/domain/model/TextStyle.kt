package com.kappdev.reminderwallpaper.wallpapers_feature.domain.model

import android.graphics.Typeface

sealed class TextStyle(val key: Int) {
    object Normal: TextStyle(Typeface.NORMAL)
    object Bold: TextStyle(Typeface.BOLD)
    object Italic: TextStyle(Typeface.ITALIC)
    object BoldItalic: TextStyle(Typeface.BOLD_ITALIC)
}