package com.example.features.nav.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.features.presentation.profile.ProfileScreen
import com.example.features.presentation.profile.screen.ContactUsScreen
import com.example.features.presentation.profile.screen.MyMembershipScreen
import com.example.features.presentation.profile.screen.PrivacyPolicyScreen
import com.example.features.presentation.profile.screen.TermsAndConditionScreen


fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    rootNavController: NavController) {

    navigation(
        startDestination = ProfileRoutes.MAIN,
        route = "profile"
    ) {
        composable(ProfileRoutes.MAIN) {
            ProfileScreen(navController,rootNavController)
        }
        composable(ProfileRoutes.MEMBERSHIP) {
            MyMembershipScreen()
        }
        composable(ProfileRoutes.CONTACT) {
            ContactUsScreen()
        }
        composable(ProfileRoutes.PRIVACY) {
            PrivacyPolicyScreen()
        }
        composable(ProfileRoutes.TERMS) {
            TermsAndConditionScreen()
        }
    }
}

object ProfileRoutes {
    const val MAIN = "profile/main"
    const val MEMBERSHIP = "profile/membership"
    const val CONTACT = "profile/contact"
    const val PRIVACY = "profile/privacy"
    const val TERMS = "profile/terms"
}
