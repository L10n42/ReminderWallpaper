package com.kappdev.reminderwallpaper.core.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(resId: Int, length: Int = DEFAULT_LENGTH) {
    Toast.makeText(this, this.getText(resId), length).show()
}

fun Context.showToast(msg: CharSequence, length: Int = DEFAULT_LENGTH) {
    Toast.makeText(this, msg, length).show()
}

private const val DEFAULT_LENGTH = Toast.LENGTH_SHORT