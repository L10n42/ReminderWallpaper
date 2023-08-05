package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.util.showToast
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.InsertWallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.MoveToImages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveWallpaperViewModel @Inject constructor(
    private val app: Application,
    private val insertWallpaper: InsertWallpaper,
    private val moveToImages: MoveToImages
) : ViewModel() {

    private var type: WallpaperType? = null
    private var storagePath: String? = null

    var cachePath by mutableStateOf<String?>(null)
        private set

    var finishActivity by mutableStateOf(false)
        private set

    fun saveWallpaper() {
        viewModelScope.launch(Dispatchers.IO) {
            moveImage()
            valid(storagePath, type) { path, type ->
                val result = insertWallpaper(
                    Wallpaper(path = path, type = type)
                )
                if (result > 0) {
                    finishActivity()
                } else {
                    showError()
                }
            }
        }
    }

    private suspend fun <P, T> valid(path: P?, type: T?, success: suspend (P, T) -> Unit) {
        if (path != null && type != null) {
            success(path, type)
        } else {
            showError()
        }
    }

    private fun showError() {
        app.showToast(R.string.couldnt_save_image)
    }

    private fun moveImage() {
        cachePath?.let { path ->
            storagePath = moveToImages(path)
        }
    }

    private fun finishActivity() {
        finishActivity = true
    }

    fun setType(type: WallpaperType?) {
        this.type = type
    }

    fun setPath(path: String?) {
        this.cachePath = path
    }
}