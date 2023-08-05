package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.new_wallpaper_screen

import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType

object WallpaperTypeCards {

    val list = listOf(
        WallpaperTypeCard(R.drawable.test_image_1, R.string.quote_title, WallpaperType.Quote),
        WallpaperTypeCard(R.drawable.test_image_2, R.string.text_title, WallpaperType.Text),
        WallpaperTypeCard(R.drawable.test_image_2, R.string.progress_title, WallpaperType.Progress)
    )
}