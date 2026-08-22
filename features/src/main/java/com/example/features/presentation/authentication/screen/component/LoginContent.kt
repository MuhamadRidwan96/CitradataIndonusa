package com.example.features.presentation.authentication.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.example.features.presentation.authentication.state.login.LoginUiAction
import com.example.features.presentation.authentication.state.login.LoginUiState
import com.example.features.presentation.authentication.utils.isValidEmail
import com.example.features.presentation.authentication.utils.isValidPassword

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    state : LoginUiState,
    onAction : (LoginUiAction) -> Unit,
    onSignUpClick:()-> Unit,
    contentPadding: PaddingValues
) {
    val focusRequester = remember { FocusRequester() }
    val isSubmitEnabled = isValidEmail(state.email)&& isValidPassword(state.password)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = 26.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        LoginFormSection(
            onEmailChange = {
                onAction(
                    LoginUiAction.EmailChanged(it)
                )
            },
            onPasswordChange = {
                onAction(
                    LoginUiAction.PasswordChanged(it)
                )
            },
            focusRequester = focusRequester,
            formState = state,
            onDone = {
                onAction(
                    LoginUiAction.Login
                )
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        LoginButtonSection(
            modifier = Modifier.padding(),
            isSubmitEnabled = isSubmitEnabled,
            isLoading = state.isLoading,
            onLoginClick = {
                onAction(
                    LoginUiAction.Login
                )
            },
            onGoogleClick = {
                onAction(
                    LoginUiAction.SignInWithGoogle
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        SignUpSection(onSignUpClick = onSignUpClick)

    }
}

