package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.home_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.GetWallpapers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWallpapers: GetWallpapers
) : ViewModel() {

    private var allWallpapers = listOf<Wallpaper>()

    var type = mutableStateOf(TypeTab.ALL)
        private set

    var wallpapers = mutableStateOf<List<Wallpaper>>(emptyList())
        private set

    private var wallpapersJob: Job? = null

    fun fetchWallpapers() {
        wallpapersJob?.cancel()
        wallpapersJob = getWallpapers().onEach { data ->
            allWallpapers = data
            changeWallpapersByType()
        }.launchIn(viewModelScope)
    }

    fun changeType(type: TypeTab) {
        this.type.value = type
    }

    fun changeWallpapersByType() {
        wallpapers.value = when (type.value) {
            TypeTab.ALL -> allWallpapers
            TypeTab.QUOTE -> allWallpapers.filter { it.type == WallpaperType.Quote }
            TypeTab.TEXT -> allWallpapers.filter { it.type == WallpaperType.Text }
            TypeTab.PROGRESS -> allWallpapers.filter { it.type == WallpaperType.Progress }
            TypeTab.POSTER -> allWallpapers.filter { it.type == WallpaperType.Poster }
        }
    }
}