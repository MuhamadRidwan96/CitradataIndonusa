package com.example.features.presentation.authentication.screen

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature_login.R
import com.example.features.presentation.authentication.AuthViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    onNavigate: (Boolean) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val scale = remember { Animatable(0.8f) }

    // Jalankan pengecekan login di awal
    LaunchedEffect(Unit) {
            viewModel.checkLogin()
    }

    // Ketika login status sudah tersedia, mulai animasi
    LaunchedEffect(isLoggedIn) {
        scale.animateTo(
            targetValue = 1.5f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300)
        )
        delay(100) // small buffer
        onNavigate(isLoggedIn)
    }

    SplashContent(scale = scale.value)
}

@Composable
private fun SplashContent(scale: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_dummy),
            contentDescription = stringResource(R.string.title_activity_screen_login),
            modifier = Modifier
                .size(200.dp)
                .scale(scale)
        )
    }
}



