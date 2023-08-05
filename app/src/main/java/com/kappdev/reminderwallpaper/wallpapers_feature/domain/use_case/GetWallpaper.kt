package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.repository.WallpapersRepository
import javax.inject.Inject

class GetWallpaper @Inject constructor(
   private val repository: WallpapersRepository
) {

    operator fun invoke(id: Long): Wallpaper? {
        return repository.getWallpaperById(id)
    }
}