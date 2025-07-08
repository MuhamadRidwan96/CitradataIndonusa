package com.example.features.nav.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.features.presentation.MainScreen
import com.example.features.presentation.profile.screen.subscreen.contact_us.ContactUsScreen
import com.example.features.presentation.profile.screen.subscreen.membership.MyMembershipScreen
import com.example.features.presentation.profile.screen.subscreen.policy.PrivacyPolicyScreen
import com.example.features.presentation.profile.screen.subscreen.terms.TermsAndConditionScreen
import com.example.features.presentation.profile.screen.subscreen.update.UpdateProfileScreen
import com.example.features.presentation.search.subscreen.ConsultantScreen
import com.example.features.presentation.search.subscreen.ContractorScreen
import com.example.features.presentation.search.subscreen.DeveloperScreen

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
        composable(ProfileRoutes.MEMBERSHIP) { MyMembershipScreen(navController = navController) }
        composable(ProfileRoutes.CONTACT) { ContactUsScreen(navController = navController) }
        composable(ProfileRoutes.PRIVACY) { PrivacyPolicyScreen(navController = navController) }
        composable(ProfileRoutes.TERMS) { TermsAndConditionScreen(navController = navController) }
        composable(ProfileRoutes.EDIT) { UpdateProfileScreen(navController = navController) }

        composable(SearchRoutes.CONTRACTOR) { ContractorScreen(navController = navController) }
        composable(SearchRoutes.DEVELOPER) { DeveloperScreen(navController = navController) }
        composable(SearchRoutes.CONSULTANT) { ConsultantScreen(navController = navController) }
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
    const val EDIT = "profile/edit"
}

object SearchRoutes {
    const val CONTRACTOR = "search/contractor"
    const val DEVELOPER = "search/developer"
    const val CONSULTANT = "search/consultant"
}