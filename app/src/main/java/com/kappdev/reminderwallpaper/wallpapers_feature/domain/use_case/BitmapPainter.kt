package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.ScreenUtils
import kotlin.math.roundToInt

open class BitmapPainter(
    context: Context
) {
    private val resources = context.resources
    private val scale = resources.displayMetrics.density
    private val screenWidth = ScreenUtils.getAbsoluteWidth(context)
    private val screenHeight = ScreenUtils.getAbsoluteHeight(context)

    protected fun staticLayoutOf(
        text: CharSequence,
        paint: TextPaint,
        width: Int,
        align: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL
    ): StaticLayout {
        return StaticLayout(text, paint, width, align, 1.0f, 0.0f, false)
    }

    protected fun Canvas.drawStaticText(dx: Float, dy: Float, layout: StaticLayout) {
        this.save()
        this.translate(dx, dy)
        layout.draw(this)
        this.restore()
    }

    protected fun createEmptyBitmap(color: Int): Bitmap {
        var bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
        bitmap = bitmap.copy(bitmap.config, true)
        bitmap.eraseColor(color)
        return bitmap
    }

    protected fun Int.toPixel(): Float {
        return (this * scale).roundToInt().toFloat()
    }

    protected companion object {
        const val DEFAULT_EDGE_PADDING = 200
    }
}