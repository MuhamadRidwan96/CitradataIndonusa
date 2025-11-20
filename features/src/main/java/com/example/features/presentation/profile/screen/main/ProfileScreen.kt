package com.example.features.presentation.profile.screen.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.feature_login.R
import com.example.features.nav.graph.Graph
import com.example.features.nav.graph.ProfileRoutes
import com.example.features.presentation.profile.LogOutViewModel
import com.example.features.presentation.profile.ProfileViewModel
import com.example.features.presentation.profile.screen.subscreen.update.ContentProfileScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                modifier = Modifier
                    .height(95.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.navigate(ProfileRoutes.EDIT) }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        ProfileScreenContent(
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun ProfileScreenContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    profileVM : ProfileViewModel = hiltViewModel(),
    viewModel: LogOutViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val profile = profileVM.userProfile.collectAsStateWithLifecycle()

    ContentProfileScreen(
        onMembershipClick = { navController.navigate(ProfileRoutes.MEMBERSHIP) },
        onContactUsClick = { navController.navigate(ProfileRoutes.CONTACT) },
        onPrivacyPolicyClick = { navController.navigate(ProfileRoutes.PRIVACY) },
        onTermsClick = { navController.navigate(ProfileRoutes.TERMS) },
        onLogout = {
            coroutineScope.launch {
                viewModel.logout()
                navController.navigate(Graph.AUTHENTICATION) {
                    popUpTo(Graph.ROOT) { inclusive = true }
                    launchSingleTop = true
                }
            }
        },
        modifier = modifier,
        fullName = profile.value.basicInfo.name,
        name = profile.value.basicInfo.fullName,
        email = profile.value.contactInfo.email,
        address = profile.value.contactInfo.address,
        company = profile.value.professionalInfo.company,
        phone = profile.value.contactInfo.phone,
        dateEnd = profile.value.subscriptionInfo.endDate,
    )
}
