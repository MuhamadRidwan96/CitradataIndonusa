package com.example.features.nav.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.features.presentation.MainScreen
import com.example.features.presentation.profile.screen.ContactUsScreen
import com.example.features.presentation.profile.screen.MyMembershipScreen
import com.example.features.presentation.profile.screen.PrivacyPolicyScreen
import com.example.features.presentation.profile.screen.TermsAndConditionScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.SPLASH
    ) {
        splashNavGraph(navController = navController)
        authNavGraph(navController = navController)
        composable(Graph.HOME) {
            MainScreen(navController)
        }
        composable(ProfileRoutes.MEMBERSHIP) { MyMembershipScreen() }
        composable(ProfileRoutes.CONTACT) { ContactUsScreen() }
        composable(ProfileRoutes.PRIVACY) { PrivacyPolicyScreen() }
        composable(ProfileRoutes.TERMS) { TermsAndConditionScreen() }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val SPLASH = "splash_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"

}

object ProfileRoutes {
    const val MEMBERSHIP = "profile/membership"
    const val CONTACT = "profile/contact"
    const val PRIVACY = "profile/privacy"
    const val TERMS = "profile/terms"
}