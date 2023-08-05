package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_progress

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.kappdev.reminderwallpaper.ui.theme.PrimaryColor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditProgressViewModel @Inject constructor(

) : ViewModel() {

    var goal = mutableStateOf("")
        private set

    var complete = mutableStateOf(0)
        private set

    var background = mutableStateOf(Color.Black)
        private set

    var chartColor = mutableStateOf(PrimaryColor)
        private set

    var textColor = mutableStateOf(Color.White)
        private set

    var sheetState = mutableStateOf<ProgressSheetState?>(null)
        private set


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

    fun setComplete(value: Int) {
        this.complete.value = value
    }

    fun setGoal(goal: String) {
        this.goal.value = goal
    }
}