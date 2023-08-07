package com.kappdev.reminderwallpaper.core.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper

class SharedViewModel : ViewModel() {

    var editWallpaper = mutableStateOf<Wallpaper?>(null)
        private set

    fun setEditWallpaper(wallpaper: Wallpaper) {
        this.editWallpaper.value = wallpaper
    }
}