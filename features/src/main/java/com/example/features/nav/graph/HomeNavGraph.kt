package com.example.features.nav.graph

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.features.nav.BottomNavItem
import com.example.features.presentation.favorite.FavoriteScreen
import com.example.features.presentation.home.screen.dashboard.HomeScreen
import com.example.features.presentation.home.screen.detail.ProjectDetailScreen
import com.example.features.presentation.home.screen.notification.NotificationScreen
import com.example.features.presentation.profile.screen.main.ProfileScreen
import com.example.features.presentation.search.SearchScreen


@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    rootNavController: NavHostController,
    projectId: String? = null,
    onScrollChange: (Boolean) -> Unit

    ) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomNavItem.Home.route
    ) {
        // Bottom navigation destinations
        homeDestination(rootNavController = rootNavController, navController = navController, onScrollChange)
        searchDestination(rootNavController = rootNavController, navController = navController)
        favoriteDestination(navController = navController)
        profileDestination(rootNavController = rootNavController)

        // Nested navigation graphs
        detailsNavGraph(navController)
        notificationDestination(navController)

    }

    LaunchedEffect(projectId) {
        if (!projectId.isNullOrBlank()) {
            navController.navigate(DetailsDestination.createRoute(projectId)) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

private fun NavGraphBuilder.homeDestination(
    rootNavController: NavHostController,
    navController: NavHostController,
    onScrollChange: (Boolean) -> Unit
) {
    composable(route = BottomNavItem.Home.route) {
        HomeScreen(
            onNavigateToLogin = {
                rootNavController.navigate(Graph.AUTHENTICATION) {
                    popUpTo(Graph.HOME) { inclusive = true }
                    launchSingleTop = true
                }
            },
            onNavigateToDetail = { projectId ->
                navController.navigate(DetailsDestination.createRoute(projectId))
            },
            onNavigateToNotification = {
                navController.navigate(NotificationDestination.createRoute())
            },
            onScrollChange = onScrollChange
        )
    }
}

private fun NavGraphBuilder.favoriteDestination(navController: NavHostController) {
    composable(route = BottomNavItem.Favorite.route) {
        FavoriteScreen(
            onNavigateToDetail = { projectId ->
                navController.navigate(DetailsDestination.createRoute(projectId))
            }
        )
    }
}


private fun NavGraphBuilder.searchDestination(
    rootNavController: NavHostController,
    navController: NavHostController
) {
    composable(route = BottomNavItem.Search.route) {
        SearchScreen(
            onNavigateToDetail = { projectId ->
                navController.navigate(DetailsDestination.createRoute(projectId))
            },
            onNavigateToLogin = {
                rootNavController.navigate(Graph.AUTHENTICATION) {
                    popUpTo(Graph.HOME) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }
}


private fun NavGraphBuilder.profileDestination(rootNavController: NavHostController) {
    composable(route = BottomNavItem.Profile.route) {
        ProfileScreen(navController = rootNavController)
    }
}


private fun NavGraphBuilder.notificationDestination(navController: NavHostController) {
    navigation(
        route = Graph.NOTIFICATION,
        startDestination = NotificationDestination.ROUTE
    ) {
        composable(route = NotificationDestination.ROUTE) {
            NotificationScreen(onBackClick = { navController.popBackStack() })
        }
    }
}

object NotificationDestination {
    const val ROUTE = "notification"
    fun createRoute(): String = "notification"
}

private fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsDestination.ROUTE
    ) {
        composable(
            route = DetailsDestination.ROUTE,
            arguments = listOf(
                navArgument(DetailsDestination.PROJECT_ID_ARG) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString(DetailsDestination.PROJECT_ID_ARG)
                ?: return@composable

            ProjectDetailScreen(
                projectId = projectId,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}

// Navigation destination definitions
object DetailsDestination {
    const val PROJECT_ID_ARG = "projectId"
    const val ROUTE = "details/{$PROJECT_ID_ARG}"

    fun createRoute(projectId: String?): String = "details/${Uri.encode(projectId)}"
}



