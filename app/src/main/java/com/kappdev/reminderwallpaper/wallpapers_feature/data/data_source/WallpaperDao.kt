package com.kappdev.reminderwallpaper.wallpapers_feature.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallpaper(wallpaper: Wallpaper): Long

    @Query("SELECT * FROM wallpapers")
    fun getWallpapers(): Flow<List<Wallpaper>>

    @Query("SELECT * FROM wallpapers WHERE id = :id LIMIT 1")
    fun getWallpaperById(id: Long): Wallpaper?

    @Delete
    suspend fun deleteWallpaper(wallpaper: Wallpaper): Int

}