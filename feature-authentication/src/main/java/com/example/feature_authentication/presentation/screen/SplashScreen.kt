package com.example.feature_authentication.presentation.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature_authentication.presentation.AuthViewModel
import com.example.feature_login.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onSplashEnd: (Boolean) -> Unit
) {
    // Animation scale
    val scale = remember { Animatable(0.8f) }
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.checkLogin()
    }

    // Start animation when composable first time appear
    LaunchedEffect(Unit) {
        // Animation scale (lil bit bigger)
        scale.animateTo(
            targetValue = 1.5f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        // Back to normal size
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300)
        )

        if (!hasNavigated) {
            delay(1000)
            hasNavigated = true
            // Callback when animation finish
            onSplashEnd(isLoggedIn)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_dummy),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(200.dp)
                .scale(scale.value)
        )
    }
}

