package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.text.TextPaint
import androidx.compose.ui.graphics.toArgb
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Poster
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.util.CacheManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PosterPainter @Inject constructor(
    @ApplicationContext private val context: Context
) : BitmapPainter(context) {

    /** @return absolute path of image in cache */
    fun draw(poster: Poster): String? {
        val bitmap = createEmptyBitmap(poster.background.toArgb())
        val canvas = Canvas(bitmap)

        // Setup text
        val textPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)
        textPaint.color = poster.foreground.toArgb()
        textPaint.textSize = poster.fontSize.toPixel()
        textPaint.typeface = Typeface.create(Typeface.SERIF, poster.style.key)

        val boxWidth = bitmap.width - DEFAULT_EDGE_PADDING
        val staticLayout = staticLayoutOf(poster.text, textPaint, boxWidth, poster.align)

        // Draw image
        var posterImage = decodeImage(poster.image)
        val imageMaxWidth = bitmap.width - DEFAULT_EDGE_PADDING
        val imageMaxHeight = bitmap.height / 2
        if (posterImage.width > imageMaxWidth || posterImage.height > imageMaxHeight) {
            posterImage = posterImage.resize(imageMaxWidth, imageMaxHeight)
        }

        val imageTop = (bitmap.height - posterImage.height - staticLayout.height - poster.fontSize.toPixel()) / 2f
        val imageLeft = (bitmap.width - posterImage.width) / 2f
        canvas.drawBitmap(posterImage, imageLeft, imageTop, null)

        // Draw text
        val boxStart = DEFAULT_EDGE_PADDING / 2f
        val textY = imageTop + posterImage.height + poster.fontSize.toPixel()
        canvas.drawStaticText(boxStart, textY, staticLayout)

        return CacheManager.cacheImage(context, bitmap)
    }

    private fun Bitmap.resize(maxWidth: Int, maxHeight: Int): Bitmap {
        val originalWidth = this.width
        val originalHeight = this.height

        // Calculate the aspect ratio
        val aspectRatio = originalWidth.toFloat() / originalHeight.toFloat()

        // Calculate new dimensions based on both max width and max height
        val newWidth: Int
        val newHeight: Int
        if (originalWidth > maxWidth || originalHeight > maxHeight) {
            val widthRatio = maxWidth.toFloat() / originalWidth.toFloat()
            val heightRatio = maxHeight.toFloat() / originalHeight.toFloat()

            if (widthRatio < heightRatio) {
                newWidth = maxWidth
                newHeight = (newWidth / aspectRatio).toInt()
            } else {
                newHeight = maxHeight
                newWidth = (newHeight * aspectRatio).toInt()
            }
        } else {
            newWidth = originalWidth
            newHeight = originalHeight
        }

        // Create a new Bitmap with the resized dimensions
        val resizedBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)

        // Scale the original Bitmap to the new Bitmap
        val canvas = Canvas(resizedBitmap)
        val scaleWidth = newWidth.toFloat() / originalWidth
        val scaleHeight = newHeight.toFloat() / originalHeight
        val scale = scaleWidth.coerceAtMost(scaleHeight)
        val xTranslation = (newWidth - originalWidth * scale) / 2.0f
        val yTranslation = (newHeight - originalHeight * scale) / 2.0f
        val transformation = Matrix()
        transformation.postTranslate(xTranslation, yTranslation)
        transformation.preScale(scale, scale)
        val paint = Paint()
        paint.isFilterBitmap = true
        canvas.drawBitmap(this, transformation, paint)

        return resizedBitmap
    }

    private fun decodeImage(uri: Uri): Bitmap {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream!!.close()
        return bitmap
    }
}