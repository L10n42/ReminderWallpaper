package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

/**
 * move image from path to images folder.
 * fun invoke() - returns path of image in images folder
 * */
class MoveToImages @Inject constructor(
    @ApplicationContext private val context: Context
){

    operator fun invoke(path: String): String? {
        val cacheImage = File(path)
        if (!cacheImage.exists()) {
            return null
        }

        val destinationDir = File(context.filesDir, IMAGES_FOLDER)
        destinationDir.ifNotExists { it.mkdirs() }

        val destinationFile = File(destinationDir, cacheImage.name)

        try {
            cacheImage.copyTo(destinationFile, true)
            cacheImage.delete()

            return destinationFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun File.ifNotExists(block: (File) -> Unit) {
        if (!this.exists()) {
            block(this)
        }
    }

    private companion object {
        const val IMAGES_FOLDER = "images"
    }
}