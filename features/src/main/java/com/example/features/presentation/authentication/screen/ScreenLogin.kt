package com.example.features.presentation.authentication.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.component.EmailTextField
import com.example.core_ui.component.GoogleLoginButton
import com.example.core_ui.component.PasswordTextField
import com.example.core_ui.component.PrimaryButton
import com.example.domain.response.AuthResponse
import com.example.feature_login.R
import com.example.features.presentation.authentication.AuthViewModel
import com.example.features.presentation.authentication.LoginEvent
import com.example.features.presentation.authentication.state.LoginUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ScreenLogin(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }

    // Handle login result dan navigasi
    LaunchedEffect(Unit) {
        delay(1000)
        viewModel.loginEvent.collectLatest { event ->
            when (event) {
                is LoginEvent.Success -> onLoginSuccess()
                is LoginEvent.Error -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }

        if (authState is AuthResponse.Success) {
            onLoginSuccess()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        LoginContent(
            uiState = uiState,
            onEmailChange = viewModel::onChangeEmail,
            onPasswordChange = viewModel::onChangePassword,
            onLoginClick = viewModel::login,
            onGoogleClick = viewModel::signWithGoogle,
            onSignUpClick = onSignUpClick,
            modifier = Modifier.padding(padding),
            onDone = viewModel::login
        )
    }
}

@Composable
fun LoginContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
    onDone: () -> Unit
) {
    val emailFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }


    LaunchedEffect(Unit) {
        emailFocus.requestFocus()
    }

    Column(
        modifier = modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginHeaderSection()
        Spacer(modifier = Modifier.height(24.dp))
        LoginFormSection(
            email = uiState.email,
            password = uiState.password,
            isEmailWrong = uiState.isEmailWrong,
            isPassWordWrong = uiState.isPassWordWrong,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            focusRequester = emailFocus,
            nextFocusRequester = passwordFocus,
            onDone = onDone,
        )
        Spacer(modifier = Modifier.height(26.dp))
        LoginButtonSection(
            isLoading = uiState.isLoading,
            onLoginClick = onLoginClick,
            onGoogleClick = onGoogleClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        SignUpSection(onSignUpClick = onSignUpClick)
    }
}

@Composable
fun LoginHeaderSection() {
    Image(
        painter = painterResource(id = R.drawable.logo_dummy),
        contentDescription = null,
        modifier = Modifier.size(150.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun LoginFormSection(
    email: String,
    password: String,
    isEmailWrong: Boolean,
    isPassWordWrong: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester?,
    onDone : () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.sign),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray
        )

        EmailTextField(
            value = email,
            isError = isEmailWrong,
            label = stringResource(id = if (isEmailWrong) R.string.wrong_email else R.string.email),
            leadingIcon = Icons.Default.Email,
            onValueChange = onEmailChange,
            focusRequester = focusRequester,
            nextFocusRequester = nextFocusRequester
        )

        PasswordTextField(
            password = password,
            leadingIcon = Icons.Default.Lock,
            isError = isPassWordWrong,
            onPasswordChange = onPasswordChange,
            label = stringResource(id = if (isPassWordWrong) R.string.pass_req else R.string.password),
            onDone = onDone,
            focusRequester = nextFocusRequester?:remember { FocusRequester() }
        )
    }
}

@Composable
fun LoginButtonSection(
    isLoading: Boolean,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PrimaryButton(
            text = stringResource(id = R.string.sign_in),
            onClick = onLoginClick,
            isLoading = isLoading
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
                    .height(1.dp),
                color = Color.Gray
            )
            Text(
                text = stringResource(id = R.string.or),
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
                    .height(1.dp),
                color = Color.Gray
            )
        }

        GoogleLoginButton(onClick = onGoogleClick)
    }
}



@Composable
fun SignUpSection(
    onSignUpClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.dont_have_account),
            style = MaterialTheme.typography.bodyMedium
        )
        TextButton(onClick = onSignUpClick) {
            Text(
                text = stringResource(id = R.string.sign_up),
                color = Color.Blue, textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}





