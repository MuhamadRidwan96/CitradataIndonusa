package com.example.features.presentation.authentication.screen.login

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.response.AuthResponse
import com.example.features.presentation.authentication.screen.component.LoginContent
import kotlinx.coroutines.flow.collectLatest


@Suppress("EffectKeys")
@Composable
fun ScreenLogin(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }

    // === Event Listener ===
    LaunchedEffect(onLoginSuccess) {
        viewModel.loginEvent.collectLatest { event ->
            when (event) {
                is LoginEvent.Success -> {
                    onLoginSuccess()
                }
                is LoginEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    // === AuthState Listener (Google Sign-in) ===
    LaunchedEffect(onLoginSuccess) {
        viewModel.authState.collectLatest { auth ->
            when (auth) {
                is AuthResponse.Success -> onLoginSuccess()
                is AuthResponse.Error -> {
                    if (auth.message.isNotBlank()) {
                        snackBarHostState.showSnackbar("Gagal login dengan google")
                    }
                }

                else -> Unit
            }
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = modifier.padding(start = 8.dp, end = 8.dp)
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    shape = RoundedCornerShape(12.dp),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    ) { padding ->

        val formState by viewModel.formState.collectAsStateWithLifecycle()
        val processState by viewModel.processState.collectAsStateWithLifecycle()
        val isSubmitted by viewModel.isSubmitEnabled.collectAsStateWithLifecycle()


        LoginContent(
            formState = formState,
            processState = processState,
            isSubmitEnabled = isSubmitted,
            onEmailChange = viewModel.onChangeEmail,
            onPasswordChange = viewModel.onChangePassword,
            onLoginClick = viewModel.login,
            onGoogleClick = viewModel.signWithGoogle,
            onSignUpClick = onSignUpClick,
            contentPadding = padding
        )
    }
}






