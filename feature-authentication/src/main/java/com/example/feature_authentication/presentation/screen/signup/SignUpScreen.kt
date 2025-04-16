package com.example.feature_authentication.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature_authentication.presentation.AuthViewModel
import com.example.feature_authentication.state.SignUpFormState
import com.example.feature_authentication.theme.Shapes
import com.example.feature_login.R


@Composable
fun SignUpScreen(
viewmodel:AuthViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            MyTopAppBar()
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FieldSection(
                    state = state,
                    onUsernameChange = onUsernameChange,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange
                )
                ButtonSection(
                    onSignUpClick = onSignUpClick,
                    onSignInClick = onSignInClick
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {


    val navigationIcon: @Composable () -> Unit = {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
    }

    val actions: @Composable RowScope.() -> Unit = {
        IconButton(onClick = { /* Handle attach */ }) {
            Icon(imageVector = Icons.Default.AttachFile, contentDescription = "Attach")
        }
        IconButton(onClick = { /* Handle calendar */ }) {
            Icon(imageVector = Icons.Default.Event, contentDescription = "Calendar")
        }
        IconButton(onClick = { /* Handle more */ }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
        }
    }

    TopAppBar(
        title = {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = navigationIcon,
        actions = actions
    )
}

@Composable
fun FieldSection(
    state: SignUpFormState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = stringResource(R.string.create_account),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.sign_ups),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        SignUpTextField(
            value = state.username,
            isError = state.isUsernameWrong,
            label = if (state.isUsernameWrong) stringResource(R.string.required_username) else stringResource(
                R.string.username
            ),
            leadingIcon = Icons.Default.Person,
            onValueChange = onUsernameChange,
        )
        SignUpTextField(
            value = state.email,
            isError = state.isEmailWrong,
            label = if (state.isEmailWrong) stringResource(R.string.wrong_email) else stringResource(
                R.string.email
            ),
            leadingIcon = Icons.Default.Email,
            onValueChange = onEmailChange,

            )

        SignUpTextField(
            value = state.password,
            isError = state.isPasswordWrong,
            label = if (state.isPasswordWrong) stringResource(R.string.wrong_password) else stringResource(
                R.string.password
            ),
            leadingIcon = Icons.Default.Lock,
            onValueChange = onPasswordChange
        )

        Text(
            text = stringResource(R.string.re_password),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
fun ButtonSection(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = onSignUpClick,
        ) {
            Icon(
                imageVector = Icons.Default.PersonAddAlt1,
                contentDescription = "Sign up icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.create_account),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.have_account),
                style = MaterialTheme.typography.bodyLarge
            )
            TextButton(onClick = onSignInClick ) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun SignUpTextField(
    value: String,
    isError: Boolean,
    label: String,
    leadingIcon: ImageVector,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        isError = isError,
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        onValueChange = onValueChange,
        label = { Text(text = label) },
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {}),
    )
}




