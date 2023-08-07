package com.kappdev.reminderwallpaper.wallpapers_feature.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.WallpaperDataConverter
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.WallpaperTypeConverter

@Entity(tableName = "wallpapers")
data class Wallpaper(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "path")
    val path: String,

    @ColumnInfo(name = "wallpaper_type")
    @TypeConverters(WallpaperTypeConverter::class)
    val type: WallpaperType,

    @ColumnInfo(name = "wallpaper_data")
    @TypeConverters(WallpaperDataConverter::class)
    val data: WallpaperData
)