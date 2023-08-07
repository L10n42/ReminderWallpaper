package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.overview_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.kappdev.reminderwallpaper.ui.theme.ReminderWallpaperTheme
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.ActivityHelper
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.overview_screen.components.OverviewImageScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewActivity : ComponentActivity() {

    companion object {
        const val WALLPAPER_ID_EXTRA = "WALLPAPER_ID_EXTRA"
        const val EDIT_RESULT = 543598
        const val EMPTY_RESULT = 543020
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val wallpaperId = intent.getLongExtra(WALLPAPER_ID_EXTRA, 0)

        setContent {
            ReminderWallpaperTheme {
                val viewModel: OverviewImageViewModel = hiltViewModel()
                val finishWithResult = viewModel.finishWithResult
                ActivityHelper.TransparentSystemBars()

                LaunchedEffect(finishWithResult) {
                    if (finishWithResult != null) {
                        this@OverviewActivity.setResult(finishWithResult)
                        this@OverviewActivity.finish()
                    }
                }

                LaunchedEffect(Unit) {
                    viewModel.fetchWallpaper(wallpaperId)
                }

                OverviewImageScreen(viewModel)
            }
        }
        ActivityHelper.hideSystemUI(window)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        ActivityHelper.hideSystemUI(window)
    }
}

fun Context.overviewActivityIntent(wallpaperId: Long): Intent {
    val overviewActivity = Intent(this, OverviewActivity::class.java)
    overviewActivity.putExtra(OverviewActivity.WALLPAPER_ID_EXTRA, wallpaperId)
    return overviewActivity
}

