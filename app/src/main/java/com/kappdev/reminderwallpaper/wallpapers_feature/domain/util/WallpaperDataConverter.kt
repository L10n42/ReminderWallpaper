package com.kappdev.reminderwallpaper.wallpapers_feature.domain.util

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperData

object WallpaperDataConverter {

    private val gson by lazy {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(WallpaperData::class.java, DataTypeAdapter())
        gsonBuilder.create()
    }

    @TypeConverter
    fun dataToString(data: WallpaperData): String {
        return gson.toJson(data, WallpaperData::class.java)
    }

    @TypeConverter
    fun stringToData(json: String): WallpaperData {
        return gson.fromJson(json, WallpaperData::class.java)
    }

}