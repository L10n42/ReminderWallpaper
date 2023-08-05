package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.new_wallpaper_screen

import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType

data class WallpaperTypeCard(
    val imageRes: Int,
    val titleRes: Int,
    val type: WallpaperType
)