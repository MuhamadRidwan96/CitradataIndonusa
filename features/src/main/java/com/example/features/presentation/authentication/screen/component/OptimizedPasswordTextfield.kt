package com.example.features.presentation.authentication.screen.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import com.example.core_ui.R
import com.example.core_ui.component.PasswordTextField

@Composable
fun OptimizedPasswordTextField(
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