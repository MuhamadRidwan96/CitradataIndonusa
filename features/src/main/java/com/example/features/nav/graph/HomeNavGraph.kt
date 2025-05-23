package com.example.features.nav.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.features.nav.BottomNavItem
import com.example.features.presentation.ScreenContent
import com.example.features.presentation.favorite.FavoriteScreen
import com.example.features.presentation.home.screen.HomeScreen
import com.example.features.presentation.profile.screen.main.ProfileScreen
import com.example.features.presentation.search.SearchScreen


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    rootNavController:NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomNavItem.Home.route
    ) {
        composable(route = BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavItem.Search.route) {
            SearchScreen(
                navController = rootNavController
            )
        }
        composable(route = BottomNavItem.Favorite.route) {
            FavoriteScreen(
                name = "BottomNavItem.Favorite.route", onClick = {}
            )
        }

        composable(route = BottomNavItem.Profile.route){
            ProfileScreen(
                navController = rootNavController
            )
        }

        detailsNavGraph(navController)
    }
}

fun NavGraphBuilder. detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(route = DetailsScreen.Information.route) {
            ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }
        }
        composable(route = DetailsScreen.Overview.route) {
            ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }
        }
    }
}

sealed class DetailsScreen(val route: String) {
    data object Information : DetailsScreen(route = "INFORMATION")
    data object Overview : DetailsScreen(route = "OVERVIEW")
}

