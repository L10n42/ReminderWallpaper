package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_text

import android.text.Layout
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Text
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.TextPosition
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.TextStyle
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.TextPainter
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.ColorPickerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditTextViewModel @Inject constructor(
    private var textPainter: TextPainter
) : ViewModel() {

    var isLoading = mutableStateOf(false)
        private set

    var text = mutableStateOf("")
        private set

    var textAlign = mutableStateOf(Layout.Alignment.ALIGN_NORMAL)
        private set

    var textPosition = mutableStateOf(TextPosition.CENTER)
        private set

    var textStyle = mutableStateOf<TextStyle>(TextStyle.NORMAL)
        private set

    var fontSize = mutableStateOf(18)
        private set

    var background = mutableStateOf(Color.Black)
        private set

    var foreground = mutableStateOf(Color.White)
        private set

    var sheetState = mutableStateOf<ColorPickerState?>(null)
        private set

    fun createWallpaper(onFinish: (path: String?, data: Text) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            var wallpaperPath: String? = null
            val text = packText()
            loading {
                wallpaperPath = textPainter.draw(text)
            }
            withContext(Dispatchers.Main) { onFinish(wallpaperPath, text) }
        }
    }

    private fun packText() = Text(
        text = text.value.trim(),
        align = textAlign.value,
        position = textPosition.value,
        style = textStyle.value,
        fontSize = fontSize.value,
        background = background.value,
        foreground = foreground.value
    )

    private fun loading(block: () -> Unit) {
        isLoading.value = true
        block()
        isLoading.value = false
    }

    fun unpackData(wallpaper: Wallpaper?) {
        if (wallpaper != null && wallpaper.data is Text) {
            text.value = wallpaper.data.text
            textAlign.value = wallpaper.data.align
            textPosition.value = wallpaper.data.position
            textStyle.value = wallpaper.data.style
            fontSize.value = wallpaper.data.fontSize
            background.value = wallpaper.data.background
            foreground.value = wallpaper.data.foreground
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

    fun setTextPosition(position: TextPosition) {
        textPosition.value = position
    }

    fun setTextAlign(align: Layout.Alignment) {
        textAlign.value = align
    }

    fun setText(text: String) {
        this.text.value = text
    }
}