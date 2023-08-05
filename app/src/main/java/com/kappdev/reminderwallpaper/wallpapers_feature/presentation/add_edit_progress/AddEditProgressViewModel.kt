package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_progress

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.reminderwallpaper.ui.theme.PrimaryColor
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Progress
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.ProgressPainter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditProgressViewModel @Inject constructor(
    private val progressPainter: ProgressPainter
) : ViewModel() {

    var isLoading = mutableStateOf(false)
        private set

    var goal = mutableStateOf("")
        private set

    var complete = mutableStateOf(0f)
        private set

    var background = mutableStateOf(Color.Black)
        private set

    var chartColor = mutableStateOf(PrimaryColor)
        private set

    var textColor = mutableStateOf(Color.White)
        private set

    var sheetState = mutableStateOf<ProgressSheetState?>(null)
        private set

    fun createWallpaper(onFinish: (path: String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            var wallpaperPath: String? = null
            loading {
                wallpaperPath = progressPainter.draw(packProgress())
            }
            withContext(Dispatchers.Main) { onFinish(wallpaperPath) }
        }
    }

    private fun packProgress() = Progress(
        goal = goal.value.trim(),
        complete = complete.value,
        background = background.value,
        textColor = textColor.value,
        chartColor = chartColor.value
    )

    private fun loading(block: () -> Unit) {
        isLoading.value = true
        block()
        isLoading.value = false
    }

    fun hideSheet() {
        sheetState.value = null
    }

    fun showSheet(sheet: ProgressSheetState) {
        sheetState.value = sheet
    }

    fun setChartColor(color: Color) {
        this.chartColor.value = color
    }

    fun setTextColor(color: Color) {
        this.textColor.value = color
    }

    fun setBackground(color: Color) {
        this.background.value = color
    }

    fun setComplete(value: Float) {
        this.complete.value = value
    }

    fun setGoal(goal: String) {
        this.goal.value = goal
    }
}