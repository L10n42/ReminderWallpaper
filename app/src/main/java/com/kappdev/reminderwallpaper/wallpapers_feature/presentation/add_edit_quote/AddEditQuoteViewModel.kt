package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_quote

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Quote
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.QuotesPainter
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.ColorPickerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditQuoteViewModel @Inject constructor(
    private var quotesPainter: QuotesPainter
) : ViewModel() {

    var isLoading = mutableStateOf(false)
        private set

    var quote = mutableStateOf("")
        private set

    var author = mutableStateOf("")
        private set

    var fontSize = mutableStateOf(18)
        private set

    var background = mutableStateOf(Color.Black)
        private set

    var foreground = mutableStateOf(Color.White)
        private set

    var sheetState = mutableStateOf<ColorPickerState?>(null)
        private set

    fun createWallpaper(onFinish: (path: String?, data: Quote) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            var wallpaperPath: String? = null
            val quote = packQuote()
            loading {
                wallpaperPath = quotesPainter.draw(quote)
            }
            withContext(Dispatchers.Main) { onFinish(wallpaperPath, quote) }
        }
    }

    private fun packQuote() = Quote(
        quote = quote.value.trim(),
        author = author.value.trim(),
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
        if (wallpaper != null && wallpaper.data is Quote) {
            quote.value = wallpaper.data.quote
            author.value = wallpaper.data.author
            fontSize.value = wallpaper.data.fontSize
            background.value = wallpaper.data.background
            foreground.value = wallpaper.data.foreground
        }
    }

    fun setForeground(color: Color) {
        foreground.value = color
    }

    fun setBackground(color: Color) {
        background.value = color
    }

    fun hideSheet() {
        sheetState.value = null
    }

    fun showSheet(sheet: ColorPickerState) {
        sheetState.value = sheet
    }

    fun setFontSize(value: Int) {
        fontSize.value = value
    }

    fun setAuthor(value: String) {
        author.value = value
    }

    fun setQuote(value: String) {
        quote.value = value
    }
}