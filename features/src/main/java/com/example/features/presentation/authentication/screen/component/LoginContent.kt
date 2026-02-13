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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.AppTheme
import com.example.features.presentation.authentication.state.LoginFormState
import com.example.features.presentation.authentication.state.LoginProcessState

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    formState: LoginFormState,
    processState: LoginProcessState,
    isSubmitEnabled: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onSignUpClick: () -> Unit,
    contentPadding: PaddingValues
) {
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = 26.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        LoginFormSection(
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            focusRequester = focusRequester,
            formState = formState,
            onDone = onLoginClick
        )

        Spacer(modifier = Modifier.height(32.dp))

        LoginButtonSection(
            modifier = Modifier.padding(),
            isSubmitEnabled = isSubmitEnabled,
            isLoading = processState.isLoading,
            onLoginClick = onLoginClick,
            onGoogleClick = onGoogleClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        SignUpSection(onSignUpClick = onSignUpClick)

    }
}

@Preview(showBackground = true)
@Composable
private fun Preview1(){
    val form = LoginFormState()
    val state = LoginProcessState()
    AppTheme {
        LoginContent(
            formState = form,
            processState = state,
            isSubmitEnabled = true,
            onEmailChange = {},
            onPasswordChange = { },
            onLoginClick = {},
            onGoogleClick = {},
            onSignUpClick ={},
            contentPadding = PaddingValues(16.dp),
        )
    }
}

