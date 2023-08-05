package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.common.components.DefaultTopBar
import com.kappdev.reminderwallpaper.core.common.components.TransparencyImageButton
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.SaveWallpaperViewModel

@Composable
fun SaveWallpaperScreen(
    viewModel: SaveWallpaperViewModel,
    goBack: () -> Unit
) {
    var showWidgets by remember { mutableStateOf(true) }
    val path = viewModel.cachePath

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                showWidgets = !showWidgets
            }
    ) {
        SubcomposeAsyncImage(
            model = path,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (showWidgets) {
            DefaultTopBar(
                title = stringResource(R.string.back_title),
                contentColor = MaterialTheme.colorScheme.primary.copy(0.7f),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding(),
                onBack = goBack
            )

            TransparencyImageButton(
                title = stringResource(R.string.save_btn),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                viewModel.saveWallpaper()
            }
        }
    }
}