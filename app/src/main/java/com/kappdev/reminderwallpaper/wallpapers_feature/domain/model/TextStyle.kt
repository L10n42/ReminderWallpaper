package com.kappdev.reminderwallpaper.wallpapers_feature.domain.model

import android.graphics.Typeface

enum class TextStyle(val key: Int) {
    NORMAL(Typeface.NORMAL),
    BOLD(Typeface.BOLD),
    ITALIC(Typeface.ITALIC),
    BOLD_ITALIC(Typeface.BOLD_ITALIC),
}