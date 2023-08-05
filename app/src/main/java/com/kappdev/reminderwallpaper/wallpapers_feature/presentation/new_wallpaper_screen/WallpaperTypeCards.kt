package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.new_wallpaper_screen

import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType

object WallpaperTypeCards {

    val list = listOf(
        WallpaperTypeCard(R.drawable.quote_example, R.string.quote_title, WallpaperType.Quote),
        WallpaperTypeCard(R.drawable.text_example, R.string.text_title, WallpaperType.Text),
        WallpaperTypeCard(R.drawable.progress_example, R.string.progress_title, WallpaperType.Progress),
        WallpaperTypeCard(R.drawable.text_example, R.string.poster_title, WallpaperType.Poster)
    )
}