package com.example.core_ui.component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalTextFieldDefaults = staticCompositionLocalOf {
    TextFieldDefault()
}

@Immutable
data class TextFieldDefault(
    val minHeight: Dp = 56.dp,
    val shape: Shape = RoundedCornerShape(8.dp)
)

@Composable
fun EmailTextField(
    value: String,
    isError: Boolean,
    label: String,
    leadingIcon: ImageVector,
    onValueChange: (String) -> Unit,
    nextFocusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions? = null,
) {
    val defaults = LocalTextFieldDefaults.current

    val keyboardOptions =
        KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
        )

    // Smart keyboard actions - use external if provided, otherwise create
    val finalKeyboardActions = keyboardActions ?: KeyboardActions(
            onNext = { nextFocusRequester?.requestFocus() },
            onDone = { /* Default behavior */ }
        )


    val borderColor =
        if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

    val textFieldColors = OutlinedTextFieldDefaults.colors(focusedBorderColor = borderColor)

    val modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = defaults.minHeight)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        leadingIcon = {
            // REFACTOR 8: Buat leading icon sebagai separate composable
            TextFieldLeadingIcon(
                icon = leadingIcon,
                isError = isError
            )
        },
        label = {
            // REFACTOR 9: Extract label untuk reusability
            TextFieldLabel(text = label)
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = finalKeyboardActions,
        isError = isError,
        singleLine = true,
        shape = defaults.shape,
        colors = textFieldColors
    )
}

@Composable
fun PasswordTextField(
    password: String,
    leadingIcon: ImageVector,
    isError: Boolean,
    onPasswordChange: (String) -> Unit,
    label: String,
    focusRequester: FocusRequester? = null,
    onDone: () -> Unit = {},
    keyboardActions: KeyboardActions? = null
) {
    val defaults = LocalTextFieldDefaults.current

    // State untuk password visibility
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    // Stable keyboard options
    val keyboardOptions = remember {
        KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
    }

    val modifier = Modifier
        .heightIn(min = defaults.minHeight)
        .fillMaxWidth()
        .let { mod ->
            if (focusRequester != null) mod.focusRequester(focusRequester) else mod
        }

    // Smart keyboard actions
    val finalKeyboardActions = keyboardActions ?: remember {
        KeyboardActions(onDone = { onDone() })
    }

    // Stable trailing icon callback
    val togglePasswordVisibility = remember {
        { passwordVisible = !passwordVisible }
    }

    val borderColor = if (isError) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = borderColor
    )

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = modifier,
        leadingIcon = {
            // REFACTOR 8: Buat leading icon sebagai separate composable
            TextFieldLeadingIcon(
                icon = leadingIcon,
                isError = isError
            )
        },
        label = {
            // REFACTOR 9: Extract label untuk reusability
            TextFieldLabel(text = label)
        },
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            PasswordVisibilityToggle(
                passwordVisible = passwordVisible,
                onToggle = togglePasswordVisibility
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = finalKeyboardActions,
        singleLine = true,
        isError = isError,
        shape = defaults.shape,
        colors = textFieldColors
    )
}

// Separate composable untuk password visibility toggle - better reusability
@Composable
private fun PasswordVisibilityToggle(
    passwordVisible: Boolean,
    onToggle: () -> Unit
) {
    val icon = if (passwordVisible) {
        Icons.Filled.Visibility
    } else {
        Icons.Filled.VisibilityOff
    }

    val contentDescription = if (passwordVisible) {
        "Hide password"
    } else {
        "Show password"
    }

    IconButton(
        onClick = onToggle,
        modifier = Modifier.semantics {
            this.contentDescription = contentDescription
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TextFieldLeadingIcon(
    icon: ImageVector,
    isError: Boolean
) {
    val tintColor =
        if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = tintColor
    )
}

@Composable
private fun TextFieldLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium
    )
}
