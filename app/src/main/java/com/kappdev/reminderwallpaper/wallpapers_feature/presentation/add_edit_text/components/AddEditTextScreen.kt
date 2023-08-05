package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_text.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
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
import com.kappdev.reminderwallpaper.core.common.components.FontSizeSlider
import com.kappdev.reminderwallpaper.core.common.components.ForegroundColorSelector
import com.kappdev.reminderwallpaper.core.common.components.LoadingDialog
import com.kappdev.reminderwallpaper.core.common.components.VerticalSpace
import com.kappdev.reminderwallpaper.core.navigation.Screen
import com.kappdev.reminderwallpaper.core.util.showToast
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_text.AddEditTextViewModel
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.ColorPickerState
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.components.AddEditScreen
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.SaveActivity
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.saveActivityIntent

@Composable
fun AddEditTextScreen(
    navController: NavHostController,
    viewModel: AddEditTextViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val text = viewModel.text.value
    val textAlign = viewModel.textAlign.value
    val textPosition = viewModel.textPosition.value
    val textStyle = viewModel.textStyle.value
    val fontSize = viewModel.fontSize.value
    val background = viewModel.background.value
    val foreground = viewModel.foreground.value
    val currentSheet = viewModel.sheetState.value
    val isLoading = viewModel.isLoading.value

    if (currentSheet != null) {
        BottomSheetController(currentSheet, viewModel)
    }

    if (isLoading) {
        LoadingDialog()
    }

    val saveWallpaperActivity = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == SaveActivity.WALLPAPER_SAVED_RESULT) {
                navController.navigate(Screen.HomeScreen.route)
            }
        }
    )

    AddEditScreen(
        title = stringResource(R.string.new_text_wallpaper_title),
        onBack = {
            navController.popBackStack()
        },
        onDone = {
            viewModel.createWallpaper { wallpaperPath ->
                if (wallpaperPath != null) {
                    saveWallpaperActivity.launch(
                        context.saveActivityIntent(wallpaperPath, WallpaperType.Text)
                    )
                } else {
                    context.showToast(R.string.something_went_wrong_msg)
                }
            }
        }
    ) {
        VerticalSpace(32.dp)
        DefaultTextField(
            value = text,
            hint = stringResource(R.string.text_hint),
            onValueChange = viewModel::setText,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalSpace(16.dp)
        TextAlignmentPicker(
            selected = textAlign,
            modifier = Modifier.fillMaxWidth(),
            onSelect = viewModel::setTextAlign
        )

        VerticalSpace(16.dp)
        TextPositionPicker(
            selected = textPosition,
            modifier = Modifier.fillMaxWidth(),
            onSelect = viewModel::setTextPosition
        )

        VerticalSpace(16.dp)
        TextStylePicker(
            style = textStyle,
            modifier = Modifier.fillMaxWidth(),
            onStyleChange = viewModel::setTextStyle
        )

        VerticalSpace(32.dp)
        FontSizeSlider(
            fontSize = fontSize,
            modifier = Modifier.fillMaxWidth(),
            range = 10f..32f,
            steps = { range ->
                ((range.endInclusive - range.start - 2f) / 2).toInt()
            },
            onSelect = viewModel::setFontSize
        )

        VerticalSpace(32.dp)
        ColorButton(
            title = stringResource(R.string.background_title),
            color = background,
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.showSheet(ColorPickerState.BackgroundColor)
        }

        VerticalSpace(16.dp)
        ColorButton(
            title = stringResource(R.string.foreground_title),
            color = foreground,
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.showSheet(ColorPickerState.ForegroundColor)
        }

        VerticalSpace(100.dp)
    }
}

@Composable
private fun BottomSheetController(
    sheet: ColorPickerState,
    viewModel: AddEditTextViewModel
) {
    when (sheet) {
        ColorPickerState.BackgroundColor -> {
            BackgroundColorSelector(
                onDismiss = viewModel::hideSheet,
                onColorSelect = viewModel::setBackground
            )
        }
        ColorPickerState.ForegroundColor -> {
            ForegroundColorSelector(
                onDismiss = viewModel::hideSheet,
                onColorSelect =viewModel::setForeground
            )
        }
    }
}