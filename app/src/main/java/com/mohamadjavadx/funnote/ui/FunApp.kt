package com.mohamadjavadx.funnote.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.mohamadjavadx.funnote.ui.destinations.AuthenticationScreenDestination
import com.mohamadjavadx.funnote.ui.theme.FunTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.ramcosta.composedestinations.spec.Route
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun FunApp() {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    //for authentication purposes
    val isAuthenticated by MutableStateFlow(true).collectAsState()
    val startRoute: Route = if (isAuthenticated) {
        NavGraphs.root.startRoute
    } else {
        NavGraphs.authentication.startRoute
    }

    FunTheme {

        DestinationsNavHost(
            engine = engine,
            navController = navController,
            navGraph = NavGraphs.root,
            startRoute = startRoute
        )

        ShowLoginWhenLoggedOut(
            isLoggedIn = isAuthenticated,
            navController = navController
        )

    }
}

@Composable
private fun ShowLoginWhenLoggedOut(
    isLoggedIn: Boolean,
    navController: NavHostController
) {
    val currentDestination by navController.appCurrentDestinationAsState()

    if (!isLoggedIn && currentDestination != AuthenticationScreenDestination) {
        navController.navigate(AuthenticationScreenDestination) {
            launchSingleTop = true
        }
    }
}