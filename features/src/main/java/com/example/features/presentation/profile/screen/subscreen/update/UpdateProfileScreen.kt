package com.example.features.presentation.profile.screen.subscreen.update

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.core_ui.component.TopAppBarWithBack
import com.example.feature_login.R

@Composable
fun UpdateProfileScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBarWithBack(title = stringResource(R.string.update_profile),
                onBackClick = { navController.popBackStack() })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .imePadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UpdateProfileComponent()
        }
    }
}
