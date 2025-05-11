package com.example.features.presentation.profile.screen.subscreen.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.AppTheme
import com.example.core_ui.component.LabeledTextField
import com.example.feature_login.R

@Composable
fun UpdateProfileComponent() {
    val focusManager = LocalFocusManager.current

    val username = rememberSaveable { mutableStateOf("") }
    val name = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ProfileHeader()

        Spacer(modifier = Modifier.height(36.dp))

        ProfileTextField(
            label = stringResource(R.string.username),
            value = username.value,
            onValueChange = { username.value = it },
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
        )

        ProfileTextField(
            label = stringResource(R.string.full_name),
            value = name.value,
            onValueChange = { name.value = it },
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
        )

        ProfileTextField(
            label = stringResource(R.string.email),
            value = email.value,
            onValueChange = { email.value = it },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            onImeAction = { focusManager.clearFocus() }
        )

        Spacer(modifier = Modifier.height(32.dp))

        UpdateButton {
            focusManager.clearFocus()
        }
    }
}

@Composable
private fun ProfileHeader() {
    val updateStyle = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary,
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text("Test User", style = updateStyle)
            Text(
                text = stringResource(R.string.share),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction,
    keyboardType: KeyboardType = KeyboardType.Text,
    onImeAction: () -> Unit
) {
    LabeledTextField(
        label = label,
        value = value,
        onValueChange = onValueChange,
        imeAction = imeAction,
        keyboardType = keyboardType,
        onImeAction = onImeAction
    )
}

@Composable
private fun UpdateButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 56.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Autorenew,
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(stringResource(R.string.update_profile))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUpdate() {
    AppTheme {
        UpdateProfileComponent()
    }
}



