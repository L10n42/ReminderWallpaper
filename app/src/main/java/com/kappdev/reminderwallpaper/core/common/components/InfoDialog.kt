package com.kappdev.reminderwallpaper.core.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.reminderwallpaper.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoDialog(
    message: String,
    title: String = stringResource(R.string.info_title),
    buttonText: String = stringResource(R.string.ok_btn),
    closeDialog: () -> Unit,
    onClick: () -> Unit,
) {
    val closeAndClick = {
        closeDialog()
        onClick()
    }

    AlertDialog(
        onDismissRequest = closeDialog
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(DialogPadding)
                )

                Text(
                    text = message,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = DialogPadding)
                )

                VerticalSpace(DialogPadding)
                Divider()

                Text(
                    text = buttonText,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = closeAndClick)
                        .padding(vertical = 12.dp)
                )
            }
        }
    }
}

private val DialogPadding = 20.dp