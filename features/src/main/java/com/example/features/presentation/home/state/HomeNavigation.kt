package com.example.features.presentation.home.state

import androidx.compose.runtime.Immutable

@Immutable
data class HomeNavigation(
   val toDetail: (String) -> Unit,
   val toNotification: () -> Unit,
   val toLogout : () -> Unit
)


@Immutable
data class ProfileNavigation(
   val toNavigateToEdit: () -> Unit,
   val toNavigateToMembership: () -> Unit,
   val toNavigateToContact: () -> Unit,
   val toNavigateToPrivacy: () -> Unit,
   val toNavigateToTerms: () -> Unit,
   val toLogout : () -> Unit
)
