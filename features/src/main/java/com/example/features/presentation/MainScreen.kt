package com.example.features.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.features.nav.MainBottomNavigation
import com.example.features.nav.destination.Screen
import com.example.features.presentation.favorite.FavoriteScreen
import com.example.features.presentation.home.screen.dashboard.HomeScreen
import com.example.features.presentation.home.screen.detail.ProjectDetailScreen
import com.example.features.presentation.home.screen.notification.NotificationScreen
import com.example.features.presentation.home.state.HomeNavigation
import com.example.features.presentation.home.state.ProfileNavigation
import com.example.features.presentation.profile.screen.main.ProfileScreen
import com.example.features.presentation.profile.screen.subscreen.contact_us.ContactUsScreen
import com.example.features.presentation.profile.screen.subscreen.membership.MembershipScreen
import com.example.features.presentation.profile.screen.subscreen.policy.PrivacyPolicyScreen
import com.example.features.presentation.profile.screen.subscreen.terms.TermsAndConditionScreen
import com.example.features.presentation.profile.screen.subscreen.update.UpdateProfileScreen
import com.example.features.presentation.search.SearchScreen

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
 *
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
    onLogout : () -> Unit
) {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    var isBottomNavVisible by remember { mutableStateOf(true) }

    LaunchedEffect(currentDestination) {
        isBottomNavVisible = when (currentDestination) {
            Screen.Home.route,
            Screen.Search.route,
            Screen.Favorite.route,
            Screen.Profile.route -> true

            else -> false
        }
    }

    /**✅ Scaffold hanya untuk content, tanpa bottom bar**/
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomNavVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(550, easing = LinearOutSlowInEasing)
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(550, easing = FastOutLinearInEasing)
                ) + fadeOut()
            ) {

                MainBottomNavigation(
                    currentDestination = currentDestination,
                    onDestinationSelect = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            startDestination = Screen.Home.route
        ) {

                composable(Screen.Home.route) {
                    HomeScreen(
                        homeNavigation = HomeNavigation(
                            toDetail = { idProject ->
                                navController.navigate(Screen.Detail.createRoute(idProject))
                            },
                            toNotification = {
                                navController.navigate(Screen.Notification.route)
                            },
                            toLogout = { onLogout() }

                        ),
                        onScrollChange = {}
                    )
                }

            composable(Screen.Search.route) {
                SearchScreen(
                    onNavigateToDetail = { idProject ->
                        navController.navigate(Screen.Detail.createRoute(idProject))
                    }
                )
            }

            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    onNavigateToDetail = { idProject ->
                        navController.navigate(Screen.Detail.createRoute(idProject))
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    profileNavigation = ProfileNavigation(
                        toNavigateToEdit = { navController.navigate(Screen.EditProfile.route) },
                        toNavigateToMembership = { navController.navigate(Screen.Membership.route) },
                        toNavigateToContact = { navController.navigate(Screen.Contact.route) },
                        toNavigateToPrivacy = { navController.navigate(Screen.Policy.route) },
                        toNavigateToTerms = { navController.navigate(Screen.Terms.route) },
                        toLogout = { onLogout() }
                    )
                )
            }

            composable(Screen.EditProfile.route) {
                UpdateProfileScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Membership.route) {
                MembershipScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Policy.route) {
                PrivacyPolicyScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Contact.route) {
                ContactUsScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.Terms.route) {
                TermsAndConditionScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }


            composable(
                Screen.Detail.route,
                arguments = listOf(navArgument("projectId") { type = NavType.StringType })
            ) {
                ProjectDetailScreen(
                    projectId = it.arguments?.getString("projectId").toString(),
                    onBackClick = {
                        navController.popBackStack()
                    },
                )
            }

            composable(Screen.Notification.route) {
                NotificationScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}


/*  // Main navigation graph that handles screen content
             HomeNavGraph(
                 navController = innerNavController,
                 rootNavController = navController,
                 projectId = projectId,
                 onScrollChange = { scrollingDown ->

                     // Hide bottom nav when scrolling down, show when scrolling up
                     isBottomNavVisible = !scrollingDown
                 }
             )*/

