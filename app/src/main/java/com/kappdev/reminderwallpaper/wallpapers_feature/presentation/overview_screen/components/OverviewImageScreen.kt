package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.overview_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.common.components.ConfirmationDialog
import com.kappdev.reminderwallpaper.core.common.components.TransparencyImageButton
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.overview_screen.OverviewImageViewModel

@Composable
fun OverviewImageScreen(
    viewModel: OverviewImageViewModel
) {
    val wallpaper = viewModel.wallpaper
    var showWidgets by remember { mutableStateOf(true) }
    var showRemoveConfirmation by remember { mutableStateOf(false) }

    if (showRemoveConfirmation) {
        ConfirmationDialog(
            title = stringResource(R.string.remove_title),
            message = stringResource(R.string.remove_msg),
            confirmText = stringResource(R.string.remove_title),
            closeDialog = { showRemoveConfirmation = false },
            onConfirm = {
                viewModel.removeCurrentWallpaper()
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                showWidgets = !showWidgets
            }
    ) {
        SubcomposeAsyncImage(
            model = wallpaper?.path,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (showWidgets) {
            OverviewTopBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding(),
                onBack = viewModel::goBack,
                onShare = viewModel::shareWallpaper,
                onRemove = {
                    showRemoveConfirmation = true
                }
            )

            TransparencyImageButton(
                title = stringResource(R.string.set_as_wallpaper),
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = viewModel::setAsWallpaper
            )
        }
    }
}

@Composable
private fun OverviewTopBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onRemove: () -> Unit,
    onShare: () -> Unit
) {
    val contentColor = MaterialTheme.colorScheme.primary.copy(0.7f)
    TopAppBar(
        elevation = 0.dp,
        modifier = modifier,
        backgroundColor = Color.Transparent,
        title = {
            Text(
                text = stringResource(R.string.back_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIos,
                    contentDescription = stringResource(R.string.back_to_home_btn),
                    tint = contentColor
                )
            }
        },
        actions = {
            IconButton(onClick = onShare) {
                Icon(
                    imageVector = Icons.Rounded.Share,
                    contentDescription = stringResource(R.string.share_wallpaper_btn),
                    tint = contentColor
                )
            }
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = stringResource(R.string.remove_wallpaper_btn),
                    tint = contentColor
                )
            }
        }
    )
}















