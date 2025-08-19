package com.example.features.nav.graph

import androidx.compose.runtime.Composable
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
import com.example.features.presentation.profile.screen.main.ProfileScreen
import com.example.features.presentation.search.SearchScreen


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomNavItem.Home.route
    ) {
        // Bottom navigation destinations
        homeDestination(
            rootNavController = rootNavController,
            navController = navController
        )
        searchDestination(rootNavController = rootNavController)
        favoriteDestination()
        profileDestination(rootNavController = rootNavController)

        // Nested navigation graphs
        detailsNavGraph(navController)
    }
}

private fun NavGraphBuilder.homeDestination(
    rootNavController: NavHostController,
    navController: NavHostController
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
            }
        )
    }
}

private fun NavGraphBuilder.searchDestination(rootNavController: NavHostController) {
    composable(route = BottomNavItem.Search.route) {
        SearchScreen(navController = rootNavController)
    }
}

private fun NavGraphBuilder.favoriteDestination() {
    composable(route = BottomNavItem.Favorite.route) {
        FavoriteScreen(
            name = "Favorites",
            onClick = { /* TODO: Implement favorite action */ }
        )
    }
}

private fun NavGraphBuilder.profileDestination(rootNavController: NavHostController) {
    composable(route = BottomNavItem.Profile.route) {
        ProfileScreen(navController = rootNavController)
    }
}

private fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsDestination.route
    ) {
        composable(
            route = DetailsDestination.route,
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
                onBackClick = {navController.popBackStack()},
            )
        }
    }
}

// Navigation destination definitions
object DetailsDestination {
    const val PROJECT_ID_ARG = "projectId"
    const val route = "details/{$PROJECT_ID_ARG}"

    fun createRoute(projectId: String): String = "details/$projectId"
}

