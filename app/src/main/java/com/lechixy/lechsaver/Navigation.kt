package com.lechixy.lechsaver

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.chibatching.kotpref.Kotpref
import com.lechixy.lechsaver.common.Route
import com.lechixy.lechsaver.page.About
import com.lechixy.lechsaver.page.Home
import com.lechixy.lechsaver.page.Settings
import com.lechixy.lechsaver.page.settings.General
import com.lechixy.lechwidgets.common.animatedComposable

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val onBackPressed: () -> Unit = { navController.popBackStack() }

    // Preferences
    Kotpref.init(LocalContext.current)

    NavHost(navController, startDestination = Route.HOME) {
        animatedComposable(Route.HOME) {
            Home(
                navigateToSettings = {
                    navController.navigate(Route.SETTINGS)
                }
            )
        }
        settingsGraph(navController = navController)
    }
}

fun NavGraphBuilder.settingsGraph(
    navController: NavHostController,
    onBackPressed: () -> Unit = { navController.popBackStack() }
) {
    navigation(startDestination = Route.SETTINGS_PAGE, route = Route.SETTINGS) {
        animatedComposable(Route.SETTINGS_PAGE) {
            Settings(
                navController,
                onBackPressed
            )
        }
        animatedComposable(Route.SETTINGS_GENERAL) {
            General(
                onBackPressed
            )
        }
        animatedComposable(Route.SETTINGS_ABOUT){
            About(
                onBackPressed
            )
        }
    }
}