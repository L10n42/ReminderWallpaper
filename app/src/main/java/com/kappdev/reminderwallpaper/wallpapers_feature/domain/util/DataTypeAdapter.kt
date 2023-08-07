package com.kappdev.reminderwallpaper.wallpapers_feature.domain.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperData
import java.lang.reflect.Type

class DataTypeAdapter : JsonSerializer<WallpaperData>, JsonDeserializer<WallpaperData> {

    override fun deserialize(jsonElement: JsonElement, type: Type, context: JsonDeserializationContext): WallpaperData {
        val jsonObj = jsonElement.asJsonObject
        val className = jsonObj[CLASS_META_KEY].asString
        return try {
            val clz = Class.forName(className)
            context.deserialize(jsonElement, clz)
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e)
        }
    }

    override fun serialize(src: WallpaperData, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonEle = context.serialize(src, src.javaClass)
        jsonEle.asJsonObject.addProperty(CLASS_META_KEY, src.javaClass.canonicalName)
        return jsonEle
    }

    companion object {
        private const val CLASS_META_KEY = "CLASS_META_KEY"
    }
}