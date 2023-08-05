package com.kappdev.reminderwallpaper.wallpapers_feature.domain.util

import android.content.Context
import android.graphics.Bitmap
import okio.IOException
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object CacheManager {

    fun cacheImage(context: Context, bitmap: Bitmap): String? {
        var outputStream: OutputStream? = null
        val cacheDir = context.cacheDir
        try {
            val cacheFilePath = File(cacheDir, "image-${System.currentTimeMillis()}.png")
            outputStream = FileOutputStream(cacheFilePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return cacheFilePath.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun deleteImage(cachePath: String) {
        val file = File(cachePath)
        if (file.exists()) {
            file.delete()
        }
    }
}