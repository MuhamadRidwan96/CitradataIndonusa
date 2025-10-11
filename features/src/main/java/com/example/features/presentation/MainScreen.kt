package com.example.features.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.features.nav.MainBottomNavigation
import com.example.features.nav.graph.HomeNavGraph
import com.example.features.nav.utils.shouldShowBottomBar
import androidx.compose.runtime.getValue


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    projectId: String? = null
) {
    val innerNavController = rememberNavController()
    val currentBackStackEntry by innerNavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val showBottomBar = remember(currentRoute) {
        shouldShowBottomBar(currentRoute)
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
            ) {
                MainBottomNavigation(innerNavController)
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    )
    { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            HomeNavGraph(
                navController = innerNavController,
                rootNavController = navController,
                projectId = projectId
            )
        }
    }
}


