package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.reminderwallpaper.core.navigation.Screen
import com.kappdev.reminderwallpaper.ui.theme.ReminderWallpaperTheme
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperData
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.valueOf
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.ActivityHelper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.CacheManager
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.WallpaperDataConverter
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.save_wallpaper_screen.components.SaveWallpaperScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveActivity : ComponentActivity() {

    companion object {
        const val IMAGE_PATH_EXTRA = "IMAGE_PATH_EXTRA"
        const val WALLPAPER_TYPE_EXTRA = "WALLPAPER_TYPE_EXTRA"
        const val WALLPAPER_DATA_EXTRA = "WALLPAPER_DATA_EXTRA"
        const val WALLPAPER_EDIT_ID_EXTRA = "WALLPAPER_EDIT_ID_EXTRA"
        const val WALLPAPER_EDIT_PATH_EXTRA = "WALLPAPER_EDIT_PATH_EXTRA"
        const val WALLPAPER_SAVED_RESULT = 37439
    }

    private lateinit var wallpaperPath: String
    private lateinit var wallpaperType: WallpaperType
    private lateinit var wallpaperData: WallpaperData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pathExtra = intent.getStringExtra(IMAGE_PATH_EXTRA)
        val typeExtra = intent.getStringExtra(WALLPAPER_TYPE_EXTRA)
        val dataJsonExtra = intent.getStringExtra(WALLPAPER_DATA_EXTRA)
        val editPath = intent.getStringExtra(WALLPAPER_EDIT_PATH_EXTRA)
        val editId = intent.getLongExtra(WALLPAPER_EDIT_ID_EXTRA, 0)

        requireNotNull(pathExtra) { "Wallpaper path can't be null" }
        requireNotNull(typeExtra) { "Wallpaper type can't be null" }
        requireNotNull(dataJsonExtra) { "Wallpaper data can't be null" }

        wallpaperPath = pathExtra
        wallpaperType = WallpaperType.valueOf(typeExtra)
        wallpaperData = WallpaperDataConverter.stringToData(dataJsonExtra)

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
                    viewModel.setWallpaperData(wallpaperData)
                    viewModel.setEditId(editId)
                    viewModel.setEditPath(editPath)
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

@Composable
fun rememberSaveActivityLauncher(navController: NavHostController) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult(),
    onResult = { result ->
        if (result.resultCode == SaveActivity.WALLPAPER_SAVED_RESULT) {
            navController.navigate(Screen.HomeScreen.route)
        }
    }
)

fun Context.saveActivityIntent(
    path: String,
    type: WallpaperType,
    data: WallpaperData,
    editPath: String?,
    editId: Long?
): Intent {
    val saveActivity = Intent(this, SaveActivity::class.java)
    saveActivity.putExtra(SaveActivity.IMAGE_PATH_EXTRA, path)
    saveActivity.putExtra(SaveActivity.WALLPAPER_TYPE_EXTRA, type.key)
    saveActivity.putExtra(SaveActivity.WALLPAPER_DATA_EXTRA, WallpaperDataConverter.dataToString(data))
    saveActivity.putExtra(SaveActivity.WALLPAPER_EDIT_PATH_EXTRA, editPath)
    saveActivity.putExtra(SaveActivity.WALLPAPER_EDIT_ID_EXTRA, editId)
    return saveActivity
}
