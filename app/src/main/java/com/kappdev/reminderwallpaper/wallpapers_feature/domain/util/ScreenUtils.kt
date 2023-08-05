package com.kappdev.reminderwallpaper.wallpapers_feature.domain.util

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

object ScreenUtils {

    fun getAbsoluteHeight(context: Context) = getFullScreenSize(context).y

    fun getAbsoluteWidth(context: Context) = getFullScreenSize(context).x

    @Suppress("DEPRECATION")
    private fun getFullScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val screenSize = Point()
        windowManager.defaultDisplay.getRealSize(screenSize)
        return screenSize
    }

}