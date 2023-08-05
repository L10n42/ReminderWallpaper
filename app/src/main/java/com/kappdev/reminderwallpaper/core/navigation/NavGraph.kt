package com.kappdev.reminderwallpaper.core.navigation

import androidx.compose.runtime.Composable
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
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Screen.NewWallpaperScreen.route) {
            NewWallpaperScreen(navController)
        }
        composable(Screen.AddEditQuoteScreen.route) {
            AddEditQuoteScreen(navController)
        }
        composable(Screen.AddEditTextScreen.route) {
            AddEditTextScreen(navController)
        }
        composable(Screen.AddEditProgressScreen.route) {
            AddEditProgressScreen(navController)
        }
        composable(Screen.AddEditPosterScreen.route) {
            AddEditPosterScreen(navController)
        }
    }
}