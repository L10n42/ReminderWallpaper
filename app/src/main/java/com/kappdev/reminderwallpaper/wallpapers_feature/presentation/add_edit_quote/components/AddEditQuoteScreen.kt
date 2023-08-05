package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_quote.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_quote.AddEditQuoteViewModel
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.ColorPickerState
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.components.AddEditScreen
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.SaveActivity
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.saveActivityIntent

@Composable
fun AddEditQuoteScreen(
    navController: NavHostController,
    viewModel: AddEditQuoteViewModel = hiltViewModel()
) {
    val quote = viewModel.quote.value
    val author = viewModel.author.value
    val fontSize = viewModel.fontSize.value
    val currentSheet = viewModel.sheetState.value
    val isLoading = viewModel.isLoading.value
    val background = viewModel.background.value
    val foreground = viewModel.foreground.value
    val context = LocalContext.current

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
        title = stringResource(R.string.new_quote_wallpaper_title),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        onBack = {
            navController.popBackStack()
        },
        onDone = {
            viewModel.createWallpaper { wallpaperPath ->
                if (wallpaperPath != null) {
                    saveWallpaperActivity.launch(
                        context.saveActivityIntent(wallpaperPath, WallpaperType.Quote)
                    )
                } else {
                    context.showToast(R.string.something_went_wrong_msg)
                }
            }
        }
    ) {
        DefaultTextField(
            value = quote,
            hint = stringResource(R.string.quote_hint),
            onValueChange = viewModel::setQuote,
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
            imeAction = ImeAction.Next,
            onImeAction = { focusManager ->
                focusManager.moveFocus(FocusDirection.Down)
            }
        )

        DefaultTextField(
            value = author,
            hint = stringResource(R.string.author_hint),
            onValueChange = viewModel::setAuthor,
            modifier = Modifier.fillMaxWidth()
        )

        FontSizeSlider(
            fontSize = fontSize,
            onSelect = viewModel::setFontSize
        )

        ColorButton(
            title = stringResource(R.string.background_title),
            color = background,
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.showSheet(ColorPickerState.BackgroundColor)
        }

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
    viewModel: AddEditQuoteViewModel
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