package com.example.features.presentation.authentication.screen.login

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_login.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val processState by viewModel.processState.collectAsStateWithLifecycle()
    var hasNavigated by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
    viewModel.checkLogin()}

    // Simple fade animation saja
    val alpha by animateFloatAsState(
        targetValue = if (processState.isReady) 1f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "alpha"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(R.drawable.logo_dummy),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(150.dp)
                .alpha(alpha)
        )
    }
    LaunchedEffect(processState.isReady) {
        if (processState.isReady && !hasNavigated) {
            hasNavigated = true
            delay(600)
            if (processState.isLoggedIn) {
                onNavigateToHome()
            } else {
                onNavigateToLogin()
            }
        }
    }
}




