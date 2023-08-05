package com.kappdev.reminderwallpaper.core.common.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            Button(confirmText) {
                closeDialog()
                onConfirm()
            }
        },
        dismissButton = {
            Button(cancelText) {
                closeDialog()
                onCancel()
            }
        }
    )
}

@Composable
private fun Button(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}