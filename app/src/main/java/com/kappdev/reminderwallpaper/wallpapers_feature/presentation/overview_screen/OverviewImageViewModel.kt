package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.overview_screen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.util.showToast
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.GetWallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.RemoveWallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.SetWallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.ShareImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewImageViewModel @Inject constructor(
    private val getWallpaper: GetWallpaper,
    private val setWallpaper: SetWallpaper,
    private val removeWallpaper: RemoveWallpaper,
    private val shareImage: ShareImage,
    private val app: Application
) : ViewModel() {

    var finishWithResult by mutableStateOf<Int?>(null)
        private set

    var wallpaper by mutableStateOf<Wallpaper?>(null)
        private set

    fun fetchWallpaper(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            wallpaper = getWallpaper(id)
            if (wallpaper == null) {
                finishWithResult(OverviewActivity.EMPTY_RESULT)
            }
        }
    }

    fun setAsWallpaper() {
        viewModelScope.launch {
            wallpaper?.path?.let { imagePath ->
                val result = setWallpaper(imagePath)
                app.showToast(result.msg)
            }
        }
    }

    fun removeCurrentWallpaper() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = wallpaper?.let { removeWallpaper(it) }
            if (result != null && result > 0) {
                finishWithResult(OverviewActivity.EMPTY_RESULT)
            } else {
                app.showToast(R.string.couldnt_remove_image)
            }
        }
    }

    fun shareWallpaper() {
        wallpaper?.path?.let {
            shareImage(it)
        }
    }

    fun goEdit() = finishWithResult(OverviewActivity.EDIT_RESULT)
    fun goBack() = finishWithResult(OverviewActivity.EMPTY_RESULT)

    private fun finishWithResult(resultCode: Int) {
        this.finishWithResult = resultCode
    }
}