package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.home_screen.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.core.navigation.Screen
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.Wallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.model.WallpaperType
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.home_screen.HomeViewModel
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.overview_screen.OverviewActivity
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.overview_screen.overviewActivityIntent

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    goToEdit: (route: String, data: Wallpaper) -> Unit
) {
    val context = LocalContext.current
    var scrollToTop by remember { mutableStateOf(true) }
    var openedWallpaper: Wallpaper? = remember { null }
    val wallpapers = viewModel.wallpapers.value

    LaunchedEffect(Unit) {
        viewModel.fetchWallpapers()
    }

    val overviewLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            when (result.resultCode) {
                OverviewActivity.EMPTY_RESULT -> {
                    openedWallpaper = null
                }
                OverviewActivity.EDIT_RESULT -> {
                    openedWallpaper?.let { wallpaper ->
                        val route = when (wallpaper.type) {
                            WallpaperType.Text -> Screen.AddEditTextScreen.route
                            WallpaperType.Poster -> Screen.AddEditPosterScreen.route
                            WallpaperType.Progress -> Screen.AddEditProgressScreen.route
                            WallpaperType.Quote -> Screen.AddEditQuoteScreen.route
                        }
                        goToEdit(route, wallpaper)
                    }
                }
            }
        }
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            HomeTopBar(
                isHeaderVisible = scrollToTop,
                changeTab = { typeTab ->
                    viewModel.changeType(typeTab)
                    viewModel.changeWallpapersByType()
                }
            )
        },
        floatingActionButton = {
            AddButton(
                isVisible = scrollToTop,
                onClick =  {
                    navController.navigate(Screen.NewWallpaperScreen.route)
                }
            )
        }
    ) { pad ->
        LazyVerticalGridWithScrollDetector(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(pad),
            onScroll = { direction ->
                scrollToTop = (direction == ScrollDirection.Top)
            }
        ) {
            items(wallpapers, { it.id }) { wallpaper ->
                ImageCard(
                    image = wallpaper.path,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    val intent = context.overviewActivityIntent(wallpaper.id)
                    openedWallpaper = wallpaper
                    overviewLauncher.launch(intent)
                }
            }
        }
    }
}

@Composable
private fun AddButton(
    isVisible: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        FloatingActionButton(
            onClick = onClick,
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(R.string.add_wallpaper_btn)
            )
        }
    }
}


















