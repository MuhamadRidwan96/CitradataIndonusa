package com.example.features.presentation.authentication.screen.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import com.example.core_ui.R
import com.example.core_ui.component.EmailTextField

@Composable
fun OptimizedEmailTextField(
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
