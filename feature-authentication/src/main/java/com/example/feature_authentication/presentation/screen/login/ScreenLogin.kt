package com.example.feature_authentication.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.CitraDataIndonusaTheme
import com.example.navigation.LocalAppNavigator
import com.example.feature_authentication.presentation.AuthViewModel
import com.example.feature_authentication.state.UiState
import com.example.feature_authentication.theme.Shapes
import com.example.feature_login.R


@Composable
fun ScreenLogin(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val loginResult by viewModel.loginResult.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val navigator = LocalAppNavigator.current

    // 🔥 Handling success dan error dalam satu LaunchedEffect
    LaunchedEffect(loginResult) {
        when (loginResult) {
            is UiState.Success -> {
                navigator.navigateToHome()
            }

            is UiState.Error -> {
                val errorMessage = (loginResult as? UiState.Error)?.message ?: "Terjadi kesalahan"
                snackBarHostState.showSnackbar(errorMessage)
            }

            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = R.drawable.logo_dummy),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            LayoutSection(
                isEmailWrong = uiState.isEmailWrong,
                isPassWordWrong = uiState.isPassWordWrong,
                email = uiState.email,
                password = uiState.password,
                onEmailChange = viewModel::onChangeUsername,
                onPasswordChange = viewModel::onChangePassword
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonSection(
                isLoading = uiState.isLoading,
                onLoginClick = { viewModel.login() }
            )
        }
    }
}

@Composable
fun ButtonSection(isLoading: Boolean, onLoginClick: () -> Unit) {

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PrimaryButton(
            text = stringResource(id = R.string.sign_in),
            onClick = onLoginClick,
            isLoading = isLoading
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
                    .height(1.dp),
                color = Color.Gray
            )
            Text(
                text = stringResource(id = R.string.or),
                modifier = Modifier.padding(horizontal = 8.dp), // Give space from Divider
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
                    .height(1.dp),
                color = Color.Gray
            )
        }
        GoogleLoginButton()
        SignUpSection()
    }
}


@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, isLoading: Boolean) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = onClick,
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White
            )
        } else {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun GoogleLoginButton() {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = { }
    ) {
        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = "Google Login"
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(id = R.string.login_with_google),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}

@Composable
fun SignUpSection() {
    val navigator = LocalAppNavigator.current

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.dont_have_account),
            style = MaterialTheme.typography.bodyLarge
        )
        TextButton(onClick = { navigator.navigateToSignUp() }) {
            Text(
                text = stringResource(id = R.string.sign_up),
                color = Color.Blue, textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun LayoutSection(
    isEmailWrong: Boolean,
    isPassWordWrong: Boolean,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {

        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .paddingFromBaseline(top = 16.dp)
                .padding(horizontal = 16.dp)
        )
        Text(
            text = stringResource(R.string.sign),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray,
            modifier = Modifier
                .paddingFromBaseline(bottom = 8.dp)
                .padding(horizontal = 16.dp)
        )

        AuthTextField(
            value = email,
            isError = isEmailWrong,
            label = if (isEmailWrong) stringResource(id = R.string.wrong_email) else stringResource(
                id = R.string.email
            ),
            leadingIcon = Icons.Default.Email,
            onValueChange = onEmailChange

        )
        PasswordTextField(
            password = password,
            leadingIcon = Icons.Default.Lock,
            isError = isPassWordWrong,
            onPasswordChange = onPasswordChange,
            label = if (isPassWordWrong) stringResource(id = R.string.wrong_password) else stringResource(
                id = R.string.password
            )
        )
    }
}

@Composable
fun PasswordTextField(
    password: String,
    leadingIcon: ImageVector,
    isError: Boolean,
    onPasswordChange: (String) -> Unit,
    label: String
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        onValueChange = onPasswordChange,
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        label = { Text(text = label) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
            }
        },
        singleLine = true,
        isError = isError
    )
}


@Composable
fun AuthTextField(
    value: String,
    isError: Boolean,
    label: String,
    leadingIcon: ImageVector,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        singleLine = true,
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        onValueChange = onValueChange,
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {}),
        isError = isError
    )
}

@Preview(showBackground = true)
@Composable
fun Preview1(){
    CitraDataIndonusaTheme {
        ScreenLogin()
    }
}


