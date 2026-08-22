package com.example.features.presentation.authentication.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_login.R
import com.example.features.presentation.authentication.screen.signup.component.ButtonSection
import com.example.features.presentation.authentication.screen.signup.component.MyTopAppBar
import com.example.features.presentation.authentication.screen.signup.component.SignUpFormSection
import com.example.features.presentation.authentication.state.signup.SignUpUiAction
import com.example.features.presentation.authentication.state.signup.SignUpUiEvent
import com.example.features.presentation.authentication.state.signup.SignUpUiState
import kotlinx.coroutines.flow.collectLatest


@Suppress("EffectKeys")
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSignInClick: () -> Unit,
    viewmodel: SignUpViewmodel = hiltViewModel()
) {
    val state by viewmodel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewmodel.uiEvent.collectLatest { event ->
            when (event) {
                is SignUpUiEvent.Success -> {
                    snackBarHostState.showSnackbar("Sign UP Success")
                }

                is SignUpUiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }

        }
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                onBackClick = { onBackClick() },
                text = stringResource(R.string.sign_in)
            )
        },
        content = { padding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                SignUpScreenContent(
                    state = state,
                    onAction = viewmodel::action,
                    onSignInClick = onSignInClick
                )
            }
        }
    )


}

@Composable
fun SignUpScreenContent(
    modifier : Modifier = Modifier,
    state: SignUpUiState,
    onAction: (SignUpUiAction) -> Unit,
    onSignInClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignUpFormSection(
            state = state,
            onUsernameChange = {
                onAction(
                SignUpUiAction.UsernameChanged(it))

            },
            onEmailChange = {
                onAction(
                    SignUpUiAction.EmailChanged(it)
                )
            },
            onPasswordChange = {
                onAction(
                    SignUpUiAction.PasswordChanged(it)
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ButtonSection(
            onSignUpClick = {
                onAction(
                    SignUpUiAction.SignUp
                )
            },
            onSignInClick = onSignInClick
        )
    }
}







