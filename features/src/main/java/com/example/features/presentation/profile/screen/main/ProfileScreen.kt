package com.example.features.presentation.profile.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.features.presentation.home.state.ProfileNavigation
import com.example.features.presentation.profile.LogOutViewModel
import com.example.features.presentation.profile.ProfileViewModel
import com.example.features.presentation.profile.screen.state.CardInfo
import com.example.features.presentation.profile.screen.subscreen.update.ContentProfileScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileNavigation: ProfileNavigation

) {
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
        ProfileScreenContent(
            onNavigateToMembership = { profileNavigation.toNavigateToMembership() },
            onNavigateToContact = { profileNavigation.toNavigateToContact() },
            onNavigateToPrivacy = { profileNavigation.toNavigateToPrivacy() },
            onNavigateToTerms = { profileNavigation.toNavigateToTerms() },
            onNavigateToLogout = { profileNavigation.toLogout() },
            onNavigateToEdit = { profileNavigation.toNavigateToEdit() },
        )

    }
}

@Composable
fun ProfileScreenContent(
    onNavigateToMembership: () -> Unit,
    onNavigateToContact: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToTerms: () -> Unit,
    onNavigateToLogout: () -> Unit,
    onNavigateToEdit : () -> Unit,


    modifier: Modifier = Modifier,

    profileVM: ProfileViewModel = hiltViewModel(),
    viewModel: LogOutViewModel = hiltViewModel()
) {

    val profile by profileVM.userProfile.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    ContentProfileScreen(
        onMembershipClick = { onNavigateToMembership() },
        onContactUsClick = { onNavigateToContact() },
        onPrivacyPolicyClick = { onNavigateToPrivacy() },
        onTermsClick = { onNavigateToTerms() },
        onLogout = {
            coroutineScope.launch {
                viewModel.logout()
                onNavigateToLogout()
            }
        },
        modifier = modifier,
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
        onEditClick  = {onNavigateToEdit() }
    )
}
