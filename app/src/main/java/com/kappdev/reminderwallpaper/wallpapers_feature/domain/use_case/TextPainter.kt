package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.text.TextPaint
import androidx.compose.ui.graphics.toArgb
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Text
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.TextPosition
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.CacheManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TextPainter @Inject constructor(
    @ApplicationContext private val context: Context
) : BitmapPainter(context) {

    /** @return absolute path of image in cache */
    fun draw(text: Text): String? {
        val bitmap = createEmptyBitmap(text.background.toArgb())
        val canvas = Canvas(bitmap)
        val paint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)

        paint.color = text.foreground.toArgb()
        paint.textSize = text.fontSize.toPixel()
        paint.typeface = Typeface.create(Typeface.SERIF, text.style.key)

        val boxWidth = bitmap.width - DEFAULT_EDGE_PADDING
        val staticLayout = staticLayoutOf(text.text, paint, boxWidth, text.align)

        val textY = when (text.position) {
            TextPosition.TOP -> DEFAULT_EDGE_PADDING / 2f
            TextPosition.CENTER -> (bitmap.height - staticLayout.height) / 2f
            TextPosition.BOTTOM -> (bitmap.height - staticLayout.height) - (DEFAULT_EDGE_PADDING / 2f)
        }

        canvas.drawStaticText(DEFAULT_EDGE_PADDING / 2f, textY, staticLayout)

        return CacheManager.cacheImage(context, bitmap)
    }
}