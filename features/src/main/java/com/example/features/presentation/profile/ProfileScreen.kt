package com.example.features.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core_ui.JakartaSans
import com.example.feature_login.R
import com.example.features.nav.graph.Graph
import com.example.features.nav.graph.ProfileRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    rootNavController: NavController,
    viewModel: LogOutViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        fontFamily = JakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color.Black,
                ),
            )
        }, content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .align(Alignment.TopCenter)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfileContent()
                    ProfileContentItems(
                        onMembershipClick = { navController.navigate(ProfileRoutes.MEMBERSHIP) },
                        onContactUsClick = { navController.navigate(ProfileRoutes.CONTACT) },
                        onPrivacyPolicyClick = { navController.navigate(ProfileRoutes.PRIVACY) },
                        onTermsClick = { navController.navigate(ProfileRoutes.TERMS) }
                    )
                    LogoutContentRow(
                        onClick = {
                            viewModel.logout()
                            rootNavController.navigate(Graph.AUTHENTICATION){
                                popUpTo(Graph.ROOT) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        })

}


