package com.kappdev.reminderwallpaper.wallpapers_feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.WallpaperTypeConverter

@Database(
    entities = [Wallpaper::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(WallpaperTypeConverter::class)
abstract class WallpaperDatabase : RoomDatabase() {

    abstract val wallpaperDao: WallpaperDao

    companion object {
        const val NAME = "wallpapers_database"
    }
}