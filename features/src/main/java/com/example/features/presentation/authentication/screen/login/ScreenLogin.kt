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

@Composable
fun ScreenLogin(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val processState by viewModel.processState.collectAsStateWithLifecycle()
    val isSubmitted by viewModel.isSubmitEnabled.collectAsStateWithLifecycle()
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loginEvent.collectLatest { event ->
            when (event) {
                is LoginEvent.Success -> onLoginSuccess()
                is LoginEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthResponse.Success -> onLoginSuccess()
            is AuthResponse.Error -> {
                if ((authState as AuthResponse.Error).message.isNotBlank()) {
                    snackBarHostState.showSnackbar("Gagal login dengan google")
                }
            }

            else -> {}
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
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






