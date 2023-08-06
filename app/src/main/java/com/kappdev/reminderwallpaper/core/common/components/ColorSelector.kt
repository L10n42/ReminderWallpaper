package com.kappdev.reminderwallpaper.core.common.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kappdev.reminderwallpaper.R
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val DefaultColors = listOf(
    Color.Black, Color.White
)

@Composable
fun ForegroundColorSelector(
    onDismiss: () -> Unit,
    onColorSelect: (Color) -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val foregroundColors = flow {
        context.dataStore.data.map { pref ->
            pref[ForegroundColorsKey].parseColors()
        }.collect {
            this.emit(it)
        }
    }.collectAsState(initial = emptyList())

    ColorSelector(
        defaultColors = DefaultColors,
        userColors = foregroundColors.value,
        addColor = { newColor ->
            scope.launch {
                addForegroundColor(context, newColor)
            }
        },
        onDismiss = onDismiss,
        onColorSelect = onColorSelect
    )
}

@Composable
fun BackgroundColorSelector(
    onDismiss: () -> Unit,
    onColorSelect: (Color) -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val backgroundColors = flow {
        context.dataStore.data.map { pref ->
            pref[BackgroundColorsKey].parseColors()
        }.collect {
            this.emit(it)
        }
    }.collectAsState(initial = emptyList())

    ColorSelector(
        defaultColors = DefaultColors,
        userColors = backgroundColors.value,
        addColor = { newColor ->
            scope.launch {
                addBackgroundColor(context, newColor)
            }
        },
        onDismiss = onDismiss,
        onColorSelect = onColorSelect
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColorSelector(
    defaultColors: List<Color>,
    userColors: List<Color>,
    addColor: (Color) -> Unit,
    onDismiss: () -> Unit,
    onColorSelect: (Color) -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val (isColorPickerVisible, showColorPicker) = remember { mutableStateOf(false) }

    if (isColorPickerVisible) {
        HsvColorPickerSheet(
            onColorPick = addColor,
            onDismiss = {
                showColorPicker(false)
            }
        )
    }

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
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 56.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(defaultColors, { it.toArgb() }) { color ->
                ColorCircle(
                    color = color,
                    onClick = {
                        onColorSelect(it)
                        dismissWithAnimation()
                    }
                )
            }
            item {
                NewColorButton {
                    showColorPicker(true)
                }
            }
            if (userColors.isNotEmpty()) {
                divider()
                items(userColors, { it.toArgb() }) { color ->
                    ColorCircle(
                        color = color,
                        onClick = {
                            onColorSelect(it)
                            dismissWithAnimation()
                        }
                    )
                }
            }
        }
    }
}

private fun LazyGridScope.divider() = item(
    span = { GridItemSpan(this.maxLineSpan) },
    content = { Divider() }
)


@Composable
private fun NewColorButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = MaterialTheme.colorScheme.onSurface
            )
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(R.string.add_color_btn),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ColorCircle(
    color: Color,
    modifier: Modifier = Modifier,
    onClick: (Color) -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .background(color, CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
            .clip(CircleShape)
            .clickable { onClick(color) }
    )
}

private val jsonType = object : TypeToken<List<Color>>() {}.type
private val Context.dataStore by preferencesDataStore("colors")

private suspend fun addBackgroundColor(context: Context, color: Color) {
    addColor(context, color, BackgroundColorsKey)
}

private suspend fun addForegroundColor(context: Context, color: Color) {
    addColor(context, color, ForegroundColorsKey)
}

private suspend fun addColor(context: Context, color: Color, key: Preferences.Key<String>) {
    context.dataStore.edit { preferences ->
        val colors = preferences[key].parseColors().toMutableList()

        colors.ifNotContains(color) {
            colors.checkBalance()
            colors.add(color)

            preferences[key] = colors.toJson()
        }
    }
}

private fun String?.parseColors(): List<Color> {
    return Gson().fromJson<List<Color>>(this, jsonType) ?: emptyList()
}

private fun MutableList<Color>.toJson(): String {
    return Gson().toJson(this, jsonType)
}

private suspend fun MutableList<Color>.ifNotContains(color: Color, block: suspend () -> Unit) {
    if (!this.contains(color)) {
        block()
    }
}

private fun MutableList<Color>.checkBalance() {
    if (this.size >= MAX_SIZE) {
        this.removeFirst()
    }
}

private const val MAX_SIZE = 30

private val BackgroundColorsKey = stringPreferencesKey("background_colors")
private val ForegroundColorsKey = stringPreferencesKey("foreground_colors")








