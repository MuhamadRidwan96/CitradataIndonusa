package com.example.features.presentation.profile.screen.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.feature_login.R
import com.example.features.nav.graph.Graph
import com.example.features.nav.graph.ProfileRoutes
import com.example.features.presentation.profile.LogOutViewModel
import com.example.features.presentation.profile.screen.subscreen.update.ContentProfile
import com.example.features.presentation.profile.screen.subscreen.update.ProfileContent
import com.example.features.presentation.profile.screen.subscreen.update.TextName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: LogOutViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
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
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                ProfileContent()
            }
            item {
                TextName(
                    name = stringResource(R.string.test_full_name),
                    username = stringResource(R.string.test_username)
                )
            }
            item {
                ContentProfile(
                    onMembershipClick = { navController.navigate(ProfileRoutes.MEMBERSHIP) },
                    onContactUsClick = { navController.navigate(ProfileRoutes.CONTACT) },
                    onPrivacyPolicyClick = { navController.navigate(ProfileRoutes.PRIVACY) },
                    onTermsClick = { navController.navigate(ProfileRoutes.TERMS) },
                    onLogout = {
                        viewModel.logout()
                        navController.navigate(Graph.AUTHENTICATION) {
                            popUpTo(Graph.ROOT) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Profile(){
    ProfileScreen(navController = rememberNavController())
}