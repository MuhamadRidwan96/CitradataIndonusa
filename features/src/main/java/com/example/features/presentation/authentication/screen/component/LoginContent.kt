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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.features.presentation.authentication.state.LoginFormState
import com.example.features.presentation.authentication.state.LoginProcessState

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
            .padding(horizontal = 32.dp)
            .testTag("LoginContent"),
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

        Spacer(modifier = Modifier.height(16.dp))

        LoginButtonSection(
            isSubmitEnabled = isSubmitEnabled,
            isLoading = processState.isLoading,
            onLoginClick = onLoginClick,
            onGoogleClick = onGoogleClick
        )
        Spacer(modifier = Modifier.height(16.dp))

        SignUpSection(onSignUpClick = onSignUpClick)

        Spacer(modifier = Modifier.height(16.dp))

    }
}

