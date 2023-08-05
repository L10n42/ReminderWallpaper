package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.new_wallpaper_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.reminderwallpaper.core.common.components.DefaultImage
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.new_wallpaper_screen.WallpaperTypeCard

@Composable
fun WallpaperTypeCard(
    card: WallpaperTypeCard,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Text(
                text = stringResource(card.titleRes),
                fontSize = 16.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(4.dp)
            )

            DefaultImage(
                model = card.imageRes,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(4.dp, 4.dp, 16.dp, 16.dp)
                    )
            )
        }
    }
}