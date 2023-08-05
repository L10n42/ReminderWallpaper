package com.kappdev.reminderwallpaper.wallpapers_feature.domain.model

sealed class WallpaperType(val key: String) {
    object Quote: WallpaperType("quote")
    object Text: WallpaperType("text")
    object Progress: WallpaperType("progress")

    companion object
}

fun WallpaperType.Companion.valueOf(key: String): WallpaperType {
    return when (key) {
        WallpaperType.Quote.key -> WallpaperType.Quote
        WallpaperType.Text.key -> WallpaperType.Text
        WallpaperType.Progress.key -> WallpaperType.Progress

        else -> throw IllegalArgumentException("Unsupported type: $key")
    }
}

