package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Layout
import android.text.TextPaint
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Progress
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.CacheManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProgressPainter @Inject constructor(
    @ApplicationContext private val context: Context
) : BitmapPainter(context) {

    /** @return absolute path of image in cache */
    fun draw(progress: Progress): String? {
        val bitmap = createEmptyBitmap(progress.background.toArgb())
        val canvas = Canvas(bitmap)
        val textPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)
        textPaint.color = progress.textColor.toArgb()

        // Prepare goal text
        textPaint.textSize = GOAL_TEXT_SIZE.toPixel()
        textPaint.typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)

        var boxWidth = bitmap.width - DEFAULT_EDGE_PADDING
        var staticLayout = staticLayoutOf(progress.goal, textPaint, boxWidth, Layout.Alignment.ALIGN_CENTER)

        // Draw chart
        val chartPaint = getDefaultChartPaint()
        chartPaint.color = progress.chartColor.copy(CHART_BACKGROUND_ALPHA).toArgb()

        val arcSize = bitmap.width / 2f
        val arcLeft = (bitmap.width - arcSize) / 2f
        val arcTop = (bitmap.height - arcSize - staticLayout.height - 24.dp.toPixels()) / 2f
        val arcRight = bitmap.width - arcLeft
        val arcBottom = arcTop + arcSize

        canvas.drawArc(arcLeft, arcTop, arcRight, arcBottom, -90f, 360f, false, chartPaint)

        val progressAngel = 360 * progress.complete
        chartPaint.color = progress.chartColor.toArgb()

        canvas.drawArc(arcLeft, arcTop, arcRight, arcBottom, -90f, progressAngel, false, chartPaint)

        // Draw goal text
        var boxStart = DEFAULT_EDGE_PADDING / 2f
        var textY = arcBottom + 24.dp.toPixels()
        canvas.drawStaticText(boxStart, textY, staticLayout)

        // Draw percent text
        textPaint.textSize = PERCENT_TEXT_SIZE.toPixel()
        textPaint.typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)

        boxWidth = (bitmap.width / 2) - DoubleStrokeWidth.toPixels().toInt()
        boxStart = (bitmap.width - boxWidth) / 2f

        val percentText = (progress.complete * 100).toInt().toString() + "%"
        staticLayout = staticLayoutOf(percentText, textPaint, boxWidth, Layout.Alignment.ALIGN_CENTER)
        textY = (arcTop + (arcSize / 2f)) - (staticLayout.height / 2f)

        canvas.drawStaticText(boxStart, textY, staticLayout)

        return CacheManager.cacheImage(context, bitmap)
    }

    private fun getDefaultChartPaint(): Paint {
        return Paint().apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = StrokeWidth.toPixels()
        }
    }

    private companion object {
        val StrokeWidth = 12.dp
        val DoubleStrokeWidth = StrokeWidth * 2

        const val GOAL_TEXT_SIZE = 18
        const val PERCENT_TEXT_SIZE = 32
        const val CHART_BACKGROUND_ALPHA = 0.16F
    }
}