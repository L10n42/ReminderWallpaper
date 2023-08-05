package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.toArgb
import android.graphics.Typeface
import android.text.Layout
import android.text.TextPaint
import androidx.compose.ui.graphics.toArgb
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Progress
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Quote
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.CacheManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.text.Typography.quote

class ProgressPainter @Inject constructor(
    @ApplicationContext private val context: Context
) : BitmapPainter(context) {

    /** @return absolute path of image in cache */
    fun draw(progress: Progress): String? {
//        val bitmap = createEmptyBitmap(progress.background.toArgb())
//        val canvas = Canvas(bitmap)
//        val paint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)
//
//        paint.color = progress.textColor.toArgb()
//        //paint.textSize = quote.fontSize.toPixel()
//        paint.typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
//
//        val boxWidth = bitmap.width - DEFAULT_EDGE_PADDING
//
//        var staticLayout = staticLayoutOf(quote.quote, paint, boxWidth)
//        var textY = (bitmap.height - staticLayout.height) / 2f
//        canvas.drawStaticText(DEFAULT_EDGE_PADDING / 2f, textY, staticLayout)
//
//        textY += staticLayout.height + quote.fontSize.toPixel()
//        staticLayout = staticLayoutOf(quote.author, paint, boxWidth, Layout.Alignment.ALIGN_OPPOSITE)
//        canvas.drawStaticText(DEFAULT_EDGE_PADDING / 2f, textY, staticLayout)
//
//        return CacheManager.cacheImage(context, bitmap)
        return null
    }
}