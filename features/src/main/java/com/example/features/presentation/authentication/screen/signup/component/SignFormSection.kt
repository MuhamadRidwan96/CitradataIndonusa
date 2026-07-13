package com.example.features.presentation.authentication.screen.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.feature_login.R
import com.example.features.presentation.authentication.state.SignUpFormState

@Composable
fun SignUpFormSection(
    modifier: Modifier = Modifier,
    state: SignUpFormState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 24.dp)) {

        Text(
            text = stringResource(R.string.create_account),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center

        )
        Text(
            text = stringResource(R.string.sign_ups),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 18.dp)
        )

        SignUpTextField(

            value = state.username,
            label = stringResource(
                R.string.username
            ),
            leadingIcon = Icons.Default.Person,
            onValueChange = { onUsernameChange(it) },
            isError = state.isUsernameWrong,
        )

        val emailLabel by remember(state.isEmailWrong){
            derivedStateOf {
                if(state.isEmailWrong) "Wrong Email" else "Email"
            }
        }
        SignUpTextField(
            value = state.email,
            isError = state.isEmailWrong,
            label = emailLabel,
            leadingIcon = Icons.Default.Email,
            onValueChange = { onEmailChange(it) },

            )

        val passwordLabel by remember (state.isPasswordWrong){
            derivedStateOf {
                if(state.isPasswordWrong) "Required Password" else "Password"
            }
        }

        SignUpTextField(
            value = state.password,
            isError = state.isPasswordWrong,
            label = passwordLabel,
            leadingIcon = Icons.Default.Lock,
            onValueChange = { onPasswordChange(it) }
        )

        Text(
            text = stringResource(R.string.re_password),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}
