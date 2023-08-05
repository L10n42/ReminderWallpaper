package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.repository.WallpapersRepository
import javax.inject.Inject

class InsertWallpaper @Inject constructor(
    private val wallpapersRepository: WallpapersRepository
) {

    suspend operator fun invoke(wallpaper: Wallpaper): Long {
        return wallpapersRepository.insertWallpaper(wallpaper)
    }
}