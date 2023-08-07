package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.util.showToast
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperData
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.InsertWallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.MoveToImages
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.ImageFileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SaveWallpaperViewModel @Inject constructor(
    private val app: Application,
    private val insertWallpaper: InsertWallpaper,
    private val moveToImages: MoveToImages
) : ViewModel() {

    private var type: WallpaperType? = null
    private var storagePath: String? = null
    private var wallpaperData: WallpaperData? = null
    private var editPath: String? = null
    private var editId: Long = 0

    var cachePath by mutableStateOf<String?>(null)
        private set

    var finishActivity by mutableStateOf(false)
        private set

    fun saveWallpaper() {
        viewModelScope.launch(Dispatchers.IO) {
            moveImage()
            valid(storagePath, type, wallpaperData) { path, type, data ->
                val result = insertWallpaper(
                    Wallpaper(id = editId, path = path, type = type, data = data)
                )
                if (result > 0) {
                    editPath?.let { ImageFileManager.remove(it) }
                    finishActivity()
                } else {
                    showError()
                }
            }
        }
    }

    private suspend fun <P, T, K> valid(path: P?, type: T?, data: K?, success: suspend (P, T, K) -> Unit) {
        if (path != null && type != null && data != null) {
            success(path, type, data)
        } else {
            showError()
        }
    }

    private suspend fun showError() {
        withContext(Dispatchers.Main) {
            app.showToast(R.string.couldnt_save_image)
        }
    }

    private fun moveImage() {
        cachePath?.let { path ->
            storagePath = moveToImages(path)
        }
    }

    private fun finishActivity() {
        finishActivity = true
    }

    fun setEditId(id: Long) {
        this.editId = id
    }

    fun setEditPath(path: String?) {
        this.editPath = path
    }

    fun setWallpaperData(data: WallpaperData) {
        this.wallpaperData = data
    }

    fun setType(type: WallpaperType?) {
        this.type = type
    }

    fun setPath(path: String?) {
        this.cachePath = path
    }
}