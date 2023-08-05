package com.kappdev.reminderwallpaper.wallpapers_feature.data.repository

import com.kappdev.reminderwallpaper.wallpapers_feature.data.data_source.WallpaperDao
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.repository.WallpapersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WallpapersRepositoryImpl @Inject constructor(
    private val wallpaperDao: WallpaperDao
) : WallpapersRepository {
    override suspend fun insertWallpaper(wallpaper: Wallpaper): Long {
        return wallpaperDao.insertWallpaper(wallpaper)
    }

    override fun getWallpapers(): Flow<List<Wallpaper>> {
        return wallpaperDao.getWallpapers()
    }

    override fun getWallpaperById(id: Long): Wallpaper? {
        return wallpaperDao.getWallpaperById(id)
    }

    override suspend fun deleteWallpaper(wallpaper: Wallpaper): Int {
        return wallpaperDao.deleteWallpaper(wallpaper)
    }
}