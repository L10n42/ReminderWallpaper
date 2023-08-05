package com.kappdev.reminderwallpaper.wallpapers_feature.domain.repository

import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow

interface WallpapersRepository {

    suspend fun insertWallpaper(wallpaper: Wallpaper): Long

    fun getWallpapers(): Flow<List<Wallpaper>>

    fun getWallpaperById(id: Long): Wallpaper?

    suspend fun deleteWallpaper(wallpaper: Wallpaper): Int
}