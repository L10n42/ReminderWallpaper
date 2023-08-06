package com.kappdev.reminderwallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kappdev.reminderwallpaper.core.navigation.Screen
import com.kappdev.reminderwallpaper.core.navigation.SetupNavGraph
import com.kappdev.reminderwallpaper.ui.theme.ReminderWallpaperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReminderWallpaperTheme {
                val navController = rememberNavController()

                SetupSystemBarColors(navController)
                SetupNavGraph(navController)
            }
        }
    }
}



@Composable
private fun SetupSystemBarColors(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val systemUiController = rememberSystemUiController()

    val statusBarColor = getStatusBarColor(currentDestination)
    val navigationBarColor = getNavigationBarColor(currentDestination)

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
        systemUiController.setNavigationBarColor(navigationBarColor)
    }
}

@Composable
private fun getNavigationBarColor(destination: String?): Color {
    return when (destination) {
        in DestinationsWithSurfaceNavigationBar -> MaterialTheme.colorScheme.surface
        else -> MaterialTheme.colorScheme.background
    }
}

@Composable
private fun getStatusBarColor(destination: String?): Color {
    return when (destination) {
        in DestinationsWithBackgroundStatusBar -> MaterialTheme.colorScheme.background
        else -> MaterialTheme.colorScheme.surface
    }
}

private val DestinationsWithSurfaceNavigationBar = listOf(
    Screen.AddEditQuoteScreen.route,
    Screen.AddEditTextScreen.route,
    Screen.AddEditProgressScreen.route,
    Screen.AddEditPosterScreen.route
)

private val DestinationsWithBackgroundStatusBar = listOf(
    Screen.NewWallpaperScreen.route
)