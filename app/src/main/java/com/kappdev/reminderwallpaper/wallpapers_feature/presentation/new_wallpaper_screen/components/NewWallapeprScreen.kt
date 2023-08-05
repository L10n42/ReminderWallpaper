package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.new_wallpaper_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.common.components.DefaultTopBar
import com.kappdev.reminderwallpaper.core.navigation.Screen
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.new_wallpaper_screen.WallpaperTypeCards

@Composable
fun NewWallpaperScreen(
    navController: NavHostController
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DefaultTopBar(
                title = stringResource(R.string.new_wallpaper_title),
                onBack = { navController.popBackStack() }
            )
        }
    ) { pad ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(ContentPadding),
            verticalArrangement = Arrangement.spacedBy(ContentPadding),
            horizontalArrangement = Arrangement.spacedBy(ContentPadding),
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
        ) {
            items(WallpaperTypeCards.list, { it.type.key }) { typeCard ->
                WallpaperTypeCard(
                    card = typeCard,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    when (typeCard.type) {
                        is WallpaperType.Quote -> navController.navigate(Screen.AddEditQuoteScreen.route)
                        is WallpaperType.Text -> navController.navigate(Screen.AddEditTextScreen.route)
                        is WallpaperType.Progress -> navController.navigate(Screen.AddEditProgressScreen.route)
                    }
                }
            }
        }
    }
}

private val ContentPadding = 16.dp

