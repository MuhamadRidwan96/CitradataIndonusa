package com.example.features.presentation.authentication.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.R
import kotlinx.coroutines.delay


@Suppress("EffectKeys")
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onCheckLogin: (Boolean) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()

) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.checkLogin()
    }

    LaunchedEffect(state.isReady,onCheckLogin) {
        if (state.isReady){
            delay(1200)
            onCheckLogin(state.isLoggedIn)
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(250.dp)
        )
    }
}




