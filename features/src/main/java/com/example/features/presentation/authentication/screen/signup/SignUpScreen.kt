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
import com.example.features.presentation.authentication.state.SignUpFormState
import kotlinx.coroutines.flow.collectLatest


@Suppress("EffectKeys")
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewmodel: SignUpViewmodel = hiltViewModel()
) {
    val state by viewmodel.formState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
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
                    onUsernameChange = viewmodel.onChangeUsername,
                    onEmailChange = viewmodel.onChangeEmail,
                    onPasswordChange = viewmodel.onChangePassword,
                    onSignUpClick = viewmodel.signUp,
                    onSignInClick = onBackClick
                )
            }
        }
    )

    LaunchedEffect(Unit) {
        viewmodel.signUpEvent.collectLatest { event ->
            when (event) {
                is SignUpEvent.Success -> {
                    " Sign up success!"
                }

                is SignUpEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }

        }
    }
}

@Composable
fun SignUpScreenContent(
    state: SignUpFormState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignUpFormSection(
            state = state,
            onUsernameChange = onUsernameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        ButtonSection(
            onSignUpClick = onSignUpClick,
            onSignInClick = onSignInClick
        )
    }
}








