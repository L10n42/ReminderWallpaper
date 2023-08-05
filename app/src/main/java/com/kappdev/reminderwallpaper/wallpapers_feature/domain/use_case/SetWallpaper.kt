package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import android.app.Application
import android.app.WallpaperManager
import android.util.Log
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.ScreenUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import javax.inject.Inject

class SetWallpaper @Inject constructor(
    private val app: Application
) {

    operator fun invoke(path: String): Result {
        val wallpaperManager = WallpaperManager.getInstance(app)
        val imageFile = File(path)
        var inputStream: FileInputStream? = null

        try {
            inputStream = FileInputStream(imageFile)

            wallpaperManager.setStream(inputStream, null, true, WallpaperManager.FLAG_SYSTEM)
            return Result.Success("wallpaper set")
        } catch (e: IOException) {
            return Result.Error("couldn't set wallpaper")
        } catch (e: FileNotFoundException) {
            return Result.Error("file not found")
        } finally {
            inputStream?.close()
        }
    }

    sealed class Result(val msg: String) {
        class Success(msg: String): Result(msg)
        class Error(msg: String): Result(msg)
    }
}