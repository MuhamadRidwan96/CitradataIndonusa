package com.example.features.presentation.profile.screen.subscreen.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_ui.component.TopAppBarWithBack
import com.example.feature_login.R
import com.example.features.presentation.profile.ProfileViewModel
import com.example.features.presentation.profile.screen.state.EditProfile

@Composable
fun UpdateProfileScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    vm: ProfileViewModel = hiltViewModel()
) {

    val profile by vm.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .statusBarsPadding()
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBarWithBack(
                    title = stringResource(R.string.update_profile),
                    onBackClick = onBackClick
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .imePadding()
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UpdateProfileComponent(
                    profileState = EditProfile(
                        name = profile.basicInfo.name,
                        fullName = profile.basicInfo.fullName,
                        email = profile.contactInfo.email,
                        username = profile.basicInfo.username
                    ),
                    usernameChange = { },
                    nameChange = { },
                    emailChange = { },
                    onUpdateClick = { }
                )
            }
        }
    }
}
