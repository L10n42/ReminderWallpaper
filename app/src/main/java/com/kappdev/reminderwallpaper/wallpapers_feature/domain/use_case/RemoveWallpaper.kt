package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.repository.WallpapersRepository
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.ImageFileManager
import java.io.File
import javax.inject.Inject

class RemoveWallpaper @Inject constructor(
    private val repository: WallpapersRepository
) {

    suspend operator fun invoke(wallpaper: Wallpaper): Int {
        val removeResult = repository.deleteWallpaper(wallpaper)
        if (removeResult > 0) {
            ImageFileManager.remove(wallpaper.path)
        }
        return removeResult
    }


}