package com.kappdev.reminderwallpaper.core.navigation

import androidx.lifecycle.ViewModel
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper

class SharedViewModel : ViewModel() {

    var editWallpaper: Wallpaper? = null
        private set


    fun clearEditWallpaper() = setEditWallpaper(null)
    fun setEditWallpaper(wallpaper: Wallpaper?) {
        this.editWallpaper = wallpaper
    }
}