package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_progress.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.common.components.BackgroundColorSelector
import com.kappdev.reminderwallpaper.core.common.components.ColorButton
import com.kappdev.reminderwallpaper.core.common.components.DefaultTextField
import com.kappdev.reminderwallpaper.core.common.components.ForegroundColorSelector
import com.kappdev.reminderwallpaper.core.common.components.LoadingDialog
import com.kappdev.reminderwallpaper.core.common.components.VerticalSpace
import com.kappdev.reminderwallpaper.core.util.showToast
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_progress.AddEditProgressViewModel
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_progress.ProgressSheetState
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.components.AddEditScreen
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.rememberSaveActivityLauncher
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.saveActivityIntent

@Composable
fun AddEditProgressScreen(
    navController: NavHostController,
    wallpaper: Wallpaper?,
    viewModel: AddEditProgressViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val goal = viewModel.goal.value
    val complete = viewModel.complete.value
    val background = viewModel.background.value
    val chartColor = viewModel.chartColor.value
    val textColor = viewModel.textColor.value
    val currentState = viewModel.sheetState.value
    val isLoading = viewModel.isLoading.value

    LaunchedEffect(Unit) {
        viewModel.unpackData(wallpaper)
    }

    if (isLoading) {
        LoadingDialog()
    }

    if (currentState != null) {
        BottomSheetController(currentState, viewModel)
    }

    val saveWallpaperActivity = rememberSaveActivityLauncher(navController)

    AddEditScreen(
        title = stringResource(R.string.new_progress_wallpaper_title),
        onBack = {
            navController.popBackStack()
        },
        onDone = {
            viewModel.createWallpaper { wallpaperPath, progress ->
                if (wallpaperPath != null) {
                    saveWallpaperActivity.launch(
                        context.saveActivityIntent(
                            path = wallpaperPath,
                            type = WallpaperType.Progress,
                            data = progress,
                            editId = wallpaper?.id,
                            editPath = wallpaper?.path
                        )
                    )
                } else {
                    context.showToast(R.string.something_went_wrong_msg)
                }
            }
        }
    ) {
        VerticalSpace(32.dp)
        DefaultTextField(
            value = goal,
            hint = stringResource(R.string.goal_hint),
            onValueChange = viewModel::setGoal,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalSpace(32.dp)
        CompleteSlider(
            complete = complete,
            modifier = Modifier.fillMaxWidth(),
            onCompleteChange = viewModel::setComplete
        )

        VerticalSpace(32.dp)
        ColorButton(
            title = stringResource(R.string.background_title),
            color = background,
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.showSheet(ProgressSheetState.BACKGROUND)
        }

        VerticalSpace(16.dp)
        ColorButton(
            title = stringResource(R.string.text_color_title),
            color = textColor,
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.showSheet(ProgressSheetState.TEXT_COLOR)
        }

        VerticalSpace(16.dp)
        ColorButton(
            title = stringResource(R.string.chart_color_title),
            color = chartColor,
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.showSheet(ProgressSheetState.CHART_COLOR)
        }

        VerticalSpace(100.dp)
    }
}

@Composable
private fun BottomSheetController(
    sheet: ProgressSheetState,
    viewModel: AddEditProgressViewModel
) {
    when (sheet) {
        ProgressSheetState.BACKGROUND -> {
            BackgroundColorSelector(
                onDismiss = viewModel::hideSheet,
                onColorSelect = viewModel::setBackground
            )
        }
        ProgressSheetState.TEXT_COLOR -> {
            ForegroundColorSelector(
                onDismiss = viewModel::hideSheet,
                onColorSelect =viewModel::setTextColor
            )
        }
        ProgressSheetState.CHART_COLOR -> {
            ForegroundColorSelector(
                onDismiss = viewModel::hideSheet,
                onColorSelect =viewModel::setChartColor
            )
        }
    }
}