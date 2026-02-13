package com.example.features.presentation.authentication.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.AppTheme
import com.example.core_ui.R
import com.example.features.presentation.authentication.state.LoginFormState

@Composable
fun LoginFormSection(
    modifier: Modifier = Modifier,
    formState: LoginFormState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onDone: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.welcome),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(R.string.sign),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
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

@Preview(showBackground = true)
@Composable
private fun PreviewContent() {
    val state = LoginFormState()
    val focusRequester = remember { FocusRequester() }
    AppTheme {

        LoginFormSection(
            formState = state,
            onEmailChange = { },
            onPasswordChange = { },
            focusRequester = focusRequester,
            onDone = { }
        )
    }
}


