package com.example.features.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.features.nav.MainBottomNavigation
import com.example.features.nav.destination.Screen
import com.example.features.presentation.favorite.screen.FavoriteScreen
import com.example.features.presentation.home.screen.dashboard.HomeScreen
import com.example.features.presentation.detail.screen.ProjectDetailScreen
import com.example.features.presentation.home.screen.notification.NotificationScreen
import com.example.features.presentation.home.utils.HomeCallbacks
import com.example.features.presentation.home.utils.ProfileCallbacks
import com.example.features.presentation.profile.screen.screen_main.ProfileScreen
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
 * - Smooth animations for bottom bar appearance/disappearance`
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
    onLogout: () -> Unit
) {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val isBottomNavVisible = when (currentDestination) {
        Screen.Home.route,
        Screen.Search.route,
        Screen.Favorite.route,
        Screen.Profile.route -> true

        else -> false
    }

    /**✅ Scaffold hanya untuk content, tanpa bottom bar**/
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomNavVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(200, easing = LinearOutSlowInEasing)
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(150, easing = FastOutLinearInEasing)
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
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            startDestination = Screen.Home.route
        ) {


            composable(
                route = Screen.Home.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(150)
                    ) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(150)
                    )
                }
            ) {
                HomeScreen(
                    homeCallbacks = HomeCallbacks(
                        navigateToDetail = { idProject ->
                            navController.navigate(Screen.Detail.createRoute(idProject))
                        },
                        navigateToNotification = {
                            navController.navigate(Screen.Notification.route)
                        },
                        navigateToLogout = { onLogout() }
                    ),
                    onScrollChange = {}
                )
            }

            composable(
                route = Screen.Search.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(150)
                    ) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(150)
                    )
                }) {
                SearchScreen(
                    onNavigateToDetail = { idProject ->
                        navController.navigate(Screen.Detail.createRoute(idProject))
                    },
                    onNavigateToLogOut = {
                        onLogout()
                    }
                )
            }

            composable(
                route = Screen.Favorite.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(150)
                    ) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(150)
                    )
                }) {
                FavoriteScreen(
                    onNavigateToDetail = { idProject ->
                        navController.navigate(Screen.Detail.createRoute(idProject))
                    }
                )
            }

            composable(
                route = Screen.Profile.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(150)
                    ) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(150)
                    )
                }) {
                ProfileScreen(
                    profileCallbacks = ProfileCallbacks(
                        toNavigateToEdit = { navController.navigate(Screen.EditProfile.route) },
                        toNavigateToMembership = { navController.navigate(Screen.Membership.route) },
                        toNavigateToContact = { navController.navigate(Screen.Contact.route) },
                        toNavigateToPrivacy = { navController.navigate(Screen.Policy.route) },
                        toNavigateToTerms = { navController.navigate(Screen.Terms.route) },
                        toLogout = {
                            onLogout()
                        }
                    )
                )
            }

            composable(
                route = Screen.EditProfile.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(150)
                    ) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(150)
                    )
                }) {
                UpdateProfileScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.Membership.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(150)
                    ) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(150)
                    )
                }
            ) {

                MembershipScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.Policy.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(150)
                    ) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(150)
                    )
                }
            ) {

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
                route = Screen.Detail.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(150)
                    ) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(150)
                    )
                },
                arguments = listOf(navArgument("projectId") { type = NavType.StringType })
            ) {
                ProjectDetailScreen(
                    projectId = it.arguments?.getString("projectId").toString(),
                    onBackClick = {
                        navController.popBackStack()
                    },
                )
            }

            composable(
                route = Screen.Notification.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(200)
                    ) + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(150)
                    )
                }
            ) {
                NotificationScreen(

                    onBackClick = {
                        navController.popBackStack()
                    },
                    onNavigateToProject = { idProject ->
                        navController.navigate(Screen.Detail.createRoute(idProject))
                    }
                )
            }
        }
    }
}




