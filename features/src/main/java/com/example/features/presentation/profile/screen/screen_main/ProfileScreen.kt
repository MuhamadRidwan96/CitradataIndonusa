package com.example.features.presentation.profile.screen.screen_main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.features.presentation.home.utils.ProfileCallbacks
import com.example.features.presentation.profile.ProfileViewModel
import com.example.features.presentation.profile.screen.state.CardInfo
import com.example.features.presentation.profile.screen.state.ProfileUiAction
import com.example.features.presentation.profile.screen.state.ProfileUiEvent
import com.example.features.presentation.profile.screen.subscreen.update.ContentProfileScreen

@Suppress("EffectKeys")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileCallbacks: ProfileCallbacks,
    modifier: Modifier = Modifier,

    profileVM: ProfileViewModel = hiltViewModel(),
) {

    val profile by profileVM.uiState.collectAsStateWithLifecycle()


    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        profileVM.uiEvent.collect { event ->
            when (event) {

                ProfileUiEvent.LogoutSuccess -> {

                    profileCallbacks.toLogout()
                }

                is ProfileUiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        event.message
                    )
                }
            }
        }
    }
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
        ContentProfileScreen(
            onMembershipClick =  profileCallbacks.toNavigateToMembership ,
            onContactUsClick = profileCallbacks.toNavigateToContact ,
            onPrivacyPolicyClick = profileCallbacks.toNavigateToPrivacy ,
            onTermsClick = profileCallbacks.toNavigateToTerms ,
            onLogout = {
                profileVM.action(
                    ProfileUiAction.Logout
                )
            },
            modifier = Modifier,
            state = CardInfo(
                name = profile.basicInfo.name,
                fullName = profile.basicInfo.fullName,
                email = profile.contactInfo.email,
                address = profile.contactInfo.address,
                company = profile.professionalInfo.company,
                phone = profile.contactInfo.phone,
                dateEnd = profile.subscriptionInfo.endDate,
                username = profile.basicInfo.username
            ),
            onEditClick = profileCallbacks.toNavigateToEdit
        )
    }
}
