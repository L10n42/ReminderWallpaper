package com.kappdev.reminderwallpaper.core.common.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.util.showToast
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HsvColorPickerSheet(
    onDismiss: () -> Unit,
    onColorPick: (Color) -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val controller = rememberColorPickerController()
    val (colorHex, setColorHex) = remember { mutableStateOf("#FFFFFFFF") }
    val (color, setColor) = remember { mutableStateOf(Color.White) }

    fun dismissWithAnimation() {
        scope.launch {
            state.hide()
            onDismiss()
        }
    }

    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = ::dismissWithAnimation,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {
            Text(
                text = "Select Color",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(bottom = 56.dp)
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                HsvColorPicker(
                    initialColor = color,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .height(450.dp)
                        .clip(CircleShape),
                    controller = controller,
                    onColorChanged = { envelope ->
                        setColor(envelope.color)
                        setColorHex(envelope.hexCode)
                    }
                )

                ColorBrick(
                    color = color,
                    hex = colorHex,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .height(36.dp)
                        .width(100.dp)
                )

                BrightnessSlider(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(24.dp),
                    controller = controller,
                )
            }

            VerticalSpace(24.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                CancelButton {
                    dismissWithAnimation()
                }
                SelectButton {
                    onColorPick(color)
                    dismissWithAnimation()
                }
            }
        }
    }
}

@Composable
private fun SelectButton(
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text(text = stringResource(R.string.select_btn))
    }
}

@Composable
private fun CancelButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = stringResource(R.string.cancel_btn))
    }
}

@Composable
private fun ColorBrick(
    color: Color,
    hex: String,
    modifier: Modifier = Modifier
) {
    val text = "#$hex"
    val context = LocalContext.current
    TextButton(
        onClick = {
            context.copyToClipboard(text)
        },
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = if (color.isBright()) Color.Black else Color.White
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun Context.copyToClipboard(text: String) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("copied_color", text)
    clipboard.setPrimaryClip(clip)
    this.showToast(R.string.copied_msg)
}

private fun Color.isBright(): Boolean {
    val red = (this.red * 255).toInt()
    val green = (this.green * 255).toInt()
    val blue = (this.blue * 255).toInt()

    val brightness = (0.299 * red + 0.587 * green + 0.114 * blue)
    return brightness >= 128
}









