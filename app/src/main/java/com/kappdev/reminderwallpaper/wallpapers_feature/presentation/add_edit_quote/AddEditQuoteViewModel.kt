package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_quote

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Quote
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

    fun createWallpaper(onFinish: (path: String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            var wallpaperPath: String? = null
            loading {
                wallpaperPath = quotesPainter.draw(packQuote())
            }
            withContext(Dispatchers.Main) { onFinish(wallpaperPath) }
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