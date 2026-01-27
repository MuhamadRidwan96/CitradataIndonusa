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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_login.R
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                modifier = modifier
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
                        IconButton(onClick = { profileNavigation.toNavigateToEdit() }) {
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
            onNavigateToMembership = { profileNavigation.toNavigateToMembership() },
            onNavigateToContact = { profileNavigation.toNavigateToContact() },
            onNavigateToPrivacy = { profileNavigation.toNavigateToPrivacy() },
            onNavigateToTerms = { profileNavigation.toNavigateToTerms() },
            onNavigateToLogout = { profileNavigation.toLogout() },
            modifier = Modifier.padding(innerPadding)
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
            fullName = profile.basicInfo.name,
            name = profile.basicInfo.fullName,
            email = profile.contactInfo.email,
            address = profile.contactInfo.address,
            company = profile.professionalInfo.company,
            phone = profile.contactInfo.phone,
            dateEnd = profile.subscriptionInfo.endDate,
        )

    )
}
