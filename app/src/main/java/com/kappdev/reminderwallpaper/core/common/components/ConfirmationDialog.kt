package com.kappdev.reminderwallpaper.core.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.reminderwallpaper.R

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    cancelText: String = stringResource(R.string.cancel_btn),
    confirmText: String = stringResource(R.string.ok_btn),
    closeDialog: () -> Unit,
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = closeDialog,
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Text(
                text = message,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        confirmButton = {
            Button(
                text = confirmText,
                color = MaterialTheme.colorScheme.error
            ) {
                closeDialog()
                onConfirm()
            }
        },
        dismissButton = {
            Button(
                text = cancelText,
                color = MaterialTheme.colorScheme.primary
            ) {
                closeDialog()
                onCancel()
            }
        }
    )
}

@Composable
private fun Button(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = color,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(2.dp)
    )
}