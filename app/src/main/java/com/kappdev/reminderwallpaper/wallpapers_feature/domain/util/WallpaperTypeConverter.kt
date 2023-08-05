package com.kappdev.reminderwallpaper.wallpapers_feature.domain.util

import androidx.room.TypeConverter
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.valueOf

object WallpaperTypeConverter {

    @TypeConverter
    fun typeToString(type: WallpaperType): String {
        return type.key
    }

    @TypeConverter
    fun stringToType(type: String): WallpaperType {
        return WallpaperType.valueOf(type)
    }

}