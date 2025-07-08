package com.example.features.presentation.authentication.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.R
import com.example.core_ui.component.EmailTextField
import com.example.core_ui.component.PasswordTextField
import com.example.domain.response.AuthResponse
import com.example.features.presentation.authentication.state.LoginFormState
import com.example.features.presentation.authentication.state.LoginProcessState
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

    LoginScreenContent(
        onSignUpClick = onSignUpClick,
        formState = formState,
        processState = processState,
        isSubmitEnabled = isSubmitted,
        onEmailChange = viewModel.onChangeEmail,
        onPasswordChange = viewModel.onChangePassword,
        onLoginClick = viewModel.login,
        onGoogleClick = viewModel.signWithGoogle,
        snackBarHostState = snackBarHostState
    )

}

@Composable
fun LoginScreenContent(
    formState: LoginFormState,
    processState: LoginProcessState,
    isSubmitEnabled: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onSignUpClick: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { padding ->
        LoginContent(
            formState = formState,
            processState = processState,
            isSubmitEnabled = isSubmitEnabled,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onLoginClick = onLoginClick,
            onGoogleClick = onGoogleClick,
            onSignUpClick = onSignUpClick,
            contentPadding = padding
        )
    }
}

@Composable
fun LoginContent(
    formState: LoginFormState,
    processState: LoginProcessState,
    isSubmitEnabled: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onSignUpClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {

        LoginFormSection(
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            focusRequester = focusRequester,
            formState = formState,
            onDone = onLoginClick
        )
        Spacer(modifier = Modifier.size(16.dp))
        LoginButtonSection(
            isSubmitEnabled = isSubmitEnabled,
            isLoading = processState.isLoading,
            onLoginClick = onLoginClick,
            onGoogleClick = onGoogleClick
        )
        Spacer(modifier = Modifier.size(16.dp))
        SignUpSection(onSignUpClick = onSignUpClick)
    }
}

@Composable
fun LoginFormSection(
    formState: LoginFormState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onDone: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.welcome),
            style = TextStyle(fontSize = 24.sp),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.sign),
            style = TextStyle(fontSize = 16.sp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        OptimizedEmailTextField(
            value = formState.email,
            isError = formState.isEmailWrong,
            onValueChange = onEmailChange,
            nextFocusRequester = focusRequester
        )
        OptimizedPasswordTextField(
            password = formState.password,
            isError = formState.isPassWordWrong,
            onPasswordChange = onPasswordChange,
            onDone = onDone,
            focusRequester = focusRequester
        )
    }
}

@Composable
private fun OptimizedEmailTextField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    nextFocusRequester: FocusRequester

) {
    val label by rememberUpdatedState(
        if (isError) stringResource(R.string.wrong_email) else stringResource(R.string.email)
    )
    val keyboardActions = remember(nextFocusRequester) {
        KeyboardActions(onNext = { nextFocusRequester.requestFocus() })
    }
    val icons = remember { Icons.Default.Email }

    EmailTextField(
        value = value,
        isError = isError,
        label = label,
        leadingIcon = icons,
        onValueChange = onValueChange,
        nextFocusRequester = nextFocusRequester,
        keyboardActions = keyboardActions,
    )
}

@Composable
private fun OptimizedPasswordTextField(
    password: String,
    isError: Boolean,
    onPasswordChange: (String) -> Unit,
    onDone: () -> Unit,
    focusRequester: FocusRequester
) {
    val label by rememberUpdatedState(
        if (isError) stringResource(R.string.wrong_password) else stringResource(R.string.password)
    )

    val keyboardActions = remember(focusRequester) {
        KeyboardActions(onDone = { onDone() })
    }
    val icons = remember { Icons.Default.Lock }

    PasswordTextField(
        password = password,
        leadingIcon = icons,
        isError = isError,
        onPasswordChange = onPasswordChange,
        label = label,
        onDone = onDone,
        focusRequester = focusRequester,
        keyboardActions = keyboardActions
    )

}

@Composable
fun LoginButtonSection(
    isLoading: Boolean,
    isSubmitEnabled: Boolean,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit

    ) {

    val textStyle = remember { TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold) }
    val painter = painterResource(id = R.drawable.google)
    val modifier = Modifier
        .height(56.dp)
        .fillMaxWidth()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Button(
            modifier = modifier,
            onClick = onLoginClick,
            enabled = isSubmitEnabled
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = stringResource(R.string.sign_in),
                        style = textStyle
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(R.string.or),
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = textStyle
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        OutlinedButton(
            onClick = onGoogleClick,
            modifier = modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()

            ) {

                Icon(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified // Preserve original colors
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.login_with_google),
                    style = textStyle
                )
            }
        }
    }
}

@Composable
fun SignUpSection(
    onSignUpClick: () -> Unit
) {
    val textStyle = TextStyle(fontSize = 16.sp)

    val rowModifier = remember {
        Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = rowModifier
    ) {
        Text(
            text =stringResource(R.string.have_account),
            style = textStyle
        )

        TextButton(onClick = onSignUpClick) {
            Text(
                text =stringResource(R.string.sign_up),
                style = textStyle
            )
        }
    }
}

