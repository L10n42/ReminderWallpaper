package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.repository.WallpapersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWallpapers @Inject constructor(
    private val wallpapersRepository: WallpapersRepository
) {

    operator fun invoke(): Flow<List<Wallpaper>> {
        return wallpapersRepository.getWallpapers()
    }
}