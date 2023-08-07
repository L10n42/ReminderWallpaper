package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_poster

import android.app.Application
import android.text.Layout
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.util.showToast
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Poster
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.TextStyle
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.PosterPainter
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.ColorPickerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditPosterViewModel @Inject constructor(
    private val app: Application,
    private val posterPainter: PosterPainter
) : ViewModel() {

    var isLoading = mutableStateOf(false)
        private set

    var text = mutableStateOf("")
        private set

    var textAlign = mutableStateOf(Layout.Alignment.ALIGN_CENTER)
        private set

    var textStyle = mutableStateOf(TextStyle.NORMAL)
        private set

    var fontSize = mutableStateOf(18)
        private set

    var background = mutableStateOf(Color.Black)
        private set

    var foreground = mutableStateOf(Color.White)
        private set

    var image = mutableStateOf<String?>(null)
        private set

    var sheetState = mutableStateOf<ColorPickerState?>(null)
        private set

    fun createWallpaper(onFinish: (path: String?, data: Poster) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            ifImageExists { imageUri ->
                var wallpaperPath: String? = null
                val poster = packPoster(imageUri)
                loading {
                    wallpaperPath = posterPainter.draw(poster)
                }
                withContext(Dispatchers.Main) { onFinish(wallpaperPath, poster) }
            }
        }
    }

    private suspend fun ifImageExists(block: suspend (String) -> Unit) {
        image.value?.let { block(it) } ?: makeError(R.string.select_image_msg)
    }

    private suspend fun makeError(res: Int) {
        withContext(Dispatchers.Main) {
            app.showToast(res)
        }
    }

    private fun packPoster(image: String) = Poster(
        text = text.value.trim(),
        align = textAlign.value,
        style = textStyle.value,
        fontSize = fontSize.value,
        background = background.value,
        foreground = foreground.value,
        image = image
    )

    private fun loading(block: () -> Unit) {
        isLoading.value = true
        block()
        isLoading.value = false
    }

    fun unpackData(wallpaper: Wallpaper?) {
        if (wallpaper != null && wallpaper.data is Poster) {
            text.value = wallpaper.data.text
            textAlign.value = wallpaper.data.align
            textStyle.value = wallpaper.data.style
            fontSize.value = wallpaper.data.fontSize
            background.value = wallpaper.data.background
            foreground.value = wallpaper.data.foreground
            image.value = wallpaper.data.image
        }
    }

    fun hideSheet() {
        sheetState.value = null
    }

    fun showSheet(sheet: ColorPickerState) {
        sheetState.value = sheet
    }

    fun setForeground(color: Color) {
        foreground.value = color
    }

    fun setBackground(color: Color) {
        background.value = color
    }

    fun setFontSize(size: Int) {
        fontSize.value = size
    }

    fun setTextStyle(style: TextStyle) {
        textStyle.value = style
    }

    fun setTextAlign(align: Layout.Alignment) {
        textAlign.value = align
    }

    fun setImage(image: String?) {
        this.image.value = image
    }

    fun setText(text: String) {
        this.text.value = text
    }
}