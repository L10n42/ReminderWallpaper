package com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case

import android.app.Application
import android.content.Intent
import androidx.core.content.FileProvider
import com.kappdev.reminderwallpaper.R
import java.io.File
import javax.inject.Inject

class ShareImage @Inject constructor(
    private val app: Application
) {
    private val shareTitle = app.getString(R.string.share_image_title)

    operator fun invoke(path: String) {
        val imageFile = File(path)
        val imageUri = FileProvider.getUriForFile(app, "${app.packageName}.fileprovider", imageFile)

        val sharingIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(Intent.EXTRA_STREAM, imageUri)
        }

        val chooserIntent = Intent.createChooser(sharingIntent, shareTitle)
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        app.startActivity(chooserIntent)
    }
}