package com.example.features.presentation.home.utils

import androidx.compose.runtime.Immutable

@Immutable
data class HomeCallbacks(
   val navigateToDetail: (String) -> Unit,
   val navigateToNotification: () -> Unit,
   val navigateToLogout : () -> Unit
)


@Immutable
data class ProfileCallbacks(
   val toNavigateToEdit: () -> Unit,
   val toNavigateToMembership: () -> Unit,
   val toNavigateToContact: () -> Unit,
   val toNavigateToPrivacy: () -> Unit,
   val toNavigateToTerms: () -> Unit,
   val toLogout : () -> Unit
)
