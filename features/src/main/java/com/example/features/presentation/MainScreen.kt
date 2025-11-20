package com.example.features.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

/**
 * Main Screen of the application that handles navigation and bottom bar visibility.
 *
 * This composable serves as the root screen that manages:
 * - Internal navigation using a nested NavHostController
 * - Conditional bottom navigation bar visibility
 * - Smooth animations for bottom bar appearance/disappearancex`
 * - Scroll-based bottom bar behavior
 *
 * @param modifier Modifier for styling and layout customization
 * @param navController The root navigation controller for top-level navigation
 * @param projectId Optional project identifier for project-specific screens,
 *        used when deep linking to a specific project
 *
 * @see HomeNavGraph for the internal navigation structure
 * @see MainBottomNavigation for the bottom navigation component
 *
 * @example
 * ```kotlin
 * MainScreen(
 *     modifier = Modifier.fillMaxSize(),
 *     navController = navController,
 *     projectId = "project-123"
 * )
 * ```
 */


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    projectId: String? = null
) {
    // Internal navigation controller for this screen's navigation graph
    val innerNavController = rememberNavController()

    // Track current back stack entry to determine current route
    val currentBackStackEntry by innerNavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Determine if bottom bar should be shown based on current route
    val showBottomBarRoute = remember(currentRoute) { shouldShowBottomBar(currentRoute) }

    // Control bottom navigation visibility based on scroll behavior
    var isBottomNavVisible by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {

        // ✅ Scaffold hanya untuk konten, tanpa bottom bar
        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = {},
            contentWindowInsets = WindowInsets(0.dp)
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .consumeWindowInsets(paddingValues)
            ) {
                // Main navigation graph that handles screen content
                HomeNavGraph(
                    navController = innerNavController,
                    rootNavController = navController,
                    projectId = projectId,
                    onScrollChange = { scrollingDown ->

                        // Hide bottom nav when scrolling down, show when scrolling up
                        isBottomNavVisible = !scrollingDown
                    }
                )
            }
        }

        // ✅ Overlay di luar Scaffold → tidak ikut padding/layout pass-nya
        // Animated bottom navigation that appears conditionally
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(), // optional: biar lebar penuh
            visible = showBottomBarRoute && isBottomNavVisible,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(550, easing = LinearOutSlowInEasing)
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(550, easing = FastOutLinearInEasing)
            ) + fadeOut()
        ) {
            MainBottomNavigation(innerNavController)
        }
    }
}


