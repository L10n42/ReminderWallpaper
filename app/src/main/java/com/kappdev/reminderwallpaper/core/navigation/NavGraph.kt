package com.kappdev.reminderwallpaper.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_poster.components.AddEditPosterScreen
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_progress.components.AddEditProgressScreen
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_quote.components.AddEditQuoteScreen
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.add_edit_text.components.AddEditTextScreen
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.home_screen.components.HomeScreen
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.new_wallpaper_screen.components.NewWallpaperScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val sharedViewModel: SharedViewModel = viewModel()
    val editWallpaper = sharedViewModel.editWallpaper.value
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController) { route, data ->
                sharedViewModel.setEditWallpaper(data)
                navController.navigate(route)
            }
        }
        composable(Screen.NewWallpaperScreen.route) {
            NewWallpaperScreen(navController)
        }

        composable(Screen.AddEditQuoteScreen.route) {
            AddEditQuoteScreen(navController, editWallpaper)
        }
        composable(Screen.AddEditTextScreen.route) {
            AddEditTextScreen(navController, editWallpaper)
        }
        composable(Screen.AddEditProgressScreen.route) {
            AddEditProgressScreen(navController, editWallpaper)
        }
        composable(Screen.AddEditPosterScreen.route) {
            AddEditPosterScreen(navController, editWallpaper)
        }
    }
}