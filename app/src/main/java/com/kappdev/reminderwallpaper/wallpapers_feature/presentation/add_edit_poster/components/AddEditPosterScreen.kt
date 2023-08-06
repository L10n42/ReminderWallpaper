package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_poster.components

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.common.components.BackgroundColorSelector
import com.kappdev.reminderwallpaper.core.common.components.ColorButton
import com.kappdev.reminderwallpaper.core.common.components.DefaultImage
import com.kappdev.reminderwallpaper.core.common.components.DefaultTextField
import com.kappdev.reminderwallpaper.core.common.components.FontSizeSlider
import com.kappdev.reminderwallpaper.core.common.components.ForegroundColorSelector
import com.kappdev.reminderwallpaper.core.common.components.InfoDialog
import com.kappdev.reminderwallpaper.core.common.components.LoadingDialog
import com.kappdev.reminderwallpaper.core.common.components.VerticalSpace
import com.kappdev.reminderwallpaper.core.util.showToast
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.BitmapPainter
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.ScreenUtils
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_poster.AddEditPosterViewModel
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.ColorPickerState
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.components.AddEditScreen
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.components.TextAlignmentPicker
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.common.components.TextStylePicker
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.rememberSaveActivityLauncher
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.saveActivityIntent

@Composable
fun AddEditPosterScreen(
    navController: NavHostController,
    viewModel: AddEditPosterViewModel = hiltViewModel()
) {
    var showImageInfoDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val text = viewModel.text.value
    val image = viewModel.image.value
    val fontSize = viewModel.fontSize.value
    val textAlign = viewModel.textAlign.value
    val isLoading = viewModel.isLoading.value
    val textStyle = viewModel.textStyle.value
    val background = viewModel.background.value
    val foreground = viewModel.foreground.value
    val currentSheet = viewModel.sheetState.value

    if (currentSheet != null) {
        BottomSheetController(currentSheet, viewModel)
    }

    if (isLoading) {
        LoadingDialog()
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { viewModel.setImage(it) }
        }
    )

    if (showImageInfoDialog) {
        InfoDialog(
            message = stringResource(R.string.image_max_size_info_msg, getRecommendImageSize(context)),
            closeDialog = { showImageInfoDialog = false },
            onClick = {  imagePicker.launch("image/*") }
        )
    }

    val saveWallpaperActivity = rememberSaveActivityLauncher(navController)

    AddEditScreen(
        title = stringResource(R.string.new_poster_wallpaper_title),
        onBack = {
            navController.popBackStack()
        },
        onDone = {
            viewModel.createWallpaper { wallpaperPath ->
                if (wallpaperPath != null) {
                    saveWallpaperActivity.launch(
                        context.saveActivityIntent(wallpaperPath, WallpaperType.Poster)
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

        VerticalSpace(16.dp)
        ImagePickerButton(
            title = stringResource(R.string.image_title),
            modifier = Modifier.fillMaxWidth()
        ) {
            showImageInfoDialog = true
        }

        if (image != null) {
            VerticalSpace(8.dp)
            DefaultImage(
                model = image,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .heightIn(50.dp, 250.dp)
                    .widthIn(50.dp, 200.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
        }

        VerticalSpace(100.dp)
    }
}

private fun getRecommendImageSize(context: Context): String {
    val width = ScreenUtils.getAbsoluteWidth(context) - BitmapPainter.DEFAULT_EDGE_PADDING
    val height = ScreenUtils.getAbsoluteHeight(context) / 2
    return width.toString() + "x" + height.toString()
}

@Composable
private fun BottomSheetController(
    sheet: ColorPickerState,
    viewModel: AddEditPosterViewModel
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