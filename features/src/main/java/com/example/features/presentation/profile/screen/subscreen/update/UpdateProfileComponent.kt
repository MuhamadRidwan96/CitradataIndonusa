package com.example.features.presentation.profile.screen.subscreen.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.LabeledTextField
import com.example.core_ui.component.StackProfile
import com.example.feature_login.R

@Composable
fun UpdateProfileComponent() {
    val username = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                StackProfile()
            }
        }
        item {
            Text(
                text = stringResource(R.string.share),
                modifier = Modifier
                    .fillMaxWidth(),  // Consistent spacing
                textAlign = TextAlign.Center,  // Center the text
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray)
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            LabeledTextField(
                label = stringResource(R.string.username),
                value = username.value,
                onValueChange = { username.value = it },
                imeAction = ImeAction.Next,
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) })
        }
        item {
            LabeledTextField(
                label = stringResource(R.string.full_name),
                value = name.value,
                onValueChange = { name.value = it },
                imeAction = ImeAction.Next,
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
            )
        }
        item {
            LabeledTextField(
                label = stringResource(R.string.email),
                value = email.value,
                onValueChange = { email.value = it },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                onImeAction = { focusManager.clearFocus() }
            )
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { focusManager.clearFocus() },
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
    }
}



