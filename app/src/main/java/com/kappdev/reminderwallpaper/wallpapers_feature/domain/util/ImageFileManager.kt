package com.kappdev.reminderwallpaper.wallpapers_feature.domain.util

import java.io.File

object ImageFileManager {

    fun remove(path: String) {
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
    }

}