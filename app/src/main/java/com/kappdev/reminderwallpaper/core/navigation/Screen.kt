package com.kappdev.reminderwallpaper.core.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object NewWallpaperScreen: Screen("new_wallpaper_screen")
    object AddEditQuoteScreen: Screen("add_edit_quote_screen")
    object AddEditTextScreen: Screen("add_edit_text_screen")
    object AddEditProgressScreen: Screen("add_edit_progress_screen")
    object AddEditPosterScreen: Screen("add_edit_poster_screen")
}