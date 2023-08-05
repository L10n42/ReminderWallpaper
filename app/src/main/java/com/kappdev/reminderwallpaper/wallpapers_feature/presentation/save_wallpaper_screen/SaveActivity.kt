package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.kappdev.reminderwallpaper.ui.theme.ReminderWallpaperTheme
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.valueOf
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.ActivityHelper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.CacheManager
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.components.SaveWallpaperScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveActivity : ComponentActivity() {

    companion object {
        const val IMAGE_PATH_EXTRA = "IMAGE_PATH_EXTRA"
        const val WALLPAPER_TYPE_EXTRA = "WALLPAPER_TYPE_EXTRA"
        const val WALLPAPER_SAVED_RESULT = 37439
    }

    private lateinit var wallpaperPath: String
    private lateinit var wallpaperType: WallpaperType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pathExtra = intent.getStringExtra(IMAGE_PATH_EXTRA)
        val typeExtra = intent.getStringExtra(WALLPAPER_TYPE_EXTRA)

        requireNotNull(pathExtra) { "Wallpaper path can't be null" }
        requireNotNull(typeExtra) { "Wallpaper type can't be null" }

        wallpaperPath = pathExtra
        wallpaperType = WallpaperType.valueOf(typeExtra)

        setContent {
            ReminderWallpaperTheme {
                val viewModel: SaveWallpaperViewModel = hiltViewModel()
                val finishActivity = viewModel.finishActivity
                ActivityHelper.TransparentSystemBars()

                LaunchedEffect(finishActivity) {
                    if (finishActivity) {
                        this@SaveActivity.setResult(WALLPAPER_SAVED_RESULT)
                        this@SaveActivity.finish()
                    }
                }

                LaunchedEffect(Unit) {
                    viewModel.setPath(wallpaperPath)
                    viewModel.setType(wallpaperType)
                }

                SaveWallpaperScreen(viewModel) {
                    this.finish()
                }
            }
        }
        ActivityHelper.hideSystemUI(window)
    }

    override fun onStop() {
        super.onStop()
        CacheManager.deleteImage(wallpaperPath)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        ActivityHelper.hideSystemUI(window)
    }
}

fun Context.saveActivityIntent(path: String, type: WallpaperType): Intent {
    val saveActivity = Intent(this, SaveActivity::class.java)
    saveActivity.putExtra(SaveActivity.IMAGE_PATH_EXTRA, path)
    saveActivity.putExtra(SaveActivity.WALLPAPER_TYPE_EXTRA, type.key)
    return saveActivity
}
