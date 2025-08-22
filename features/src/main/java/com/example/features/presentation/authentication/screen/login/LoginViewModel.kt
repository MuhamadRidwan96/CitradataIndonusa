package com.example.features.presentation.authentication.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.response.AuthResponse
import com.example.domain.usecase.authentication.CheckLoginUseCase
import com.example.domain.usecase.authentication.GoogleSignInUseCase
import com.example.domain.usecase.authentication.LoginUseCase
import com.example.features.presentation.authentication.state.LoginFormState
import com.example.features.presentation.authentication.state.LoginProcessState
import com.example.features.presentation.authentication.state.UiState
import com.example.features.presentation.authentication.utils.isValidEmail
import com.example.features.presentation.authentication.utils.isValidPassword
import com.example.features.presentation.authentication.utils.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkLoginUseCase: CheckLoginUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase
) : ViewModel() {

    private val _loginEvent =
        Channel<LoginEvent>(Channel.BUFFERED) // ✅ BUFFERED untuk mencegah kehilangan event
    val loginEvent = _loginEvent.receiveAsFlow()

    private val _formState = MutableStateFlow(LoginFormState())
    val formState = _formState.asStateFlow()

    private val _processState = MutableStateFlow(LoginProcessState())
    val processState = _processState.asStateFlow()

    private val _authState = MutableStateFlow<AuthResponse?>(null)
    val authState = _authState.asStateFlow()

    //make more simple with 1 combine
    val isSubmitEnabled: StateFlow<Boolean> = formState.map { state ->
        isValidEmail(state.email) && isValidPassword(state.password)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )


    val onChangeEmail: (String) -> Unit = { newEmail ->
        _formState.update {
            it.copy(
                email = newEmail,
                isEmailWrong = newEmail.isNotEmpty() && !isValidEmail(newEmail),
                errorMessage = null
            )
        }
    }

    val onChangePassword: (String) -> Unit = { newPassword ->
        _formState.update {
            it.copy(
                password = newPassword,
                isPassWordWrong = newPassword.isNotEmpty() && !isValidPassword(newPassword),
                errorMessage = null
            )
        }
    }

    val login: () -> Unit = login@{
        if (!isSubmitEnabled.value) return@login
        viewModelScope.launch {

            loginUseCase(_formState.value.email, _formState.value.password)
                .flowOn(Dispatchers.IO)
                .toUiState()
                .onStart {
                    setLoading(true)
                }
                .onCompletion {
                    delay(2000)
                    setLoading(false)
                }
                .collectLatest { result ->
                    when (result) {
                        is UiState.Success -> {
                            _processState.update { it.copy(isLoggedIn = true) }
                            _loginEvent.send(LoginEvent.Success)
                        }

                        is UiState.Error -> {
                            _formState.update { it.copy(errorMessage = result.message) }
                            _loginEvent.send(LoginEvent.ShowSnackBar(result.message))
                        }

                        else -> Unit
                    }
                }
        }
    }


    fun checkLogin() {
        viewModelScope.launch {
            val isLoggedIn = withContext(Dispatchers.IO) { checkLoginUseCase() }
            _processState.update {
                it.copy(isLoggedIn = isLoggedIn, isReady = true)
            }
        }
    }

    val signWithGoogle: () -> Unit = {
        viewModelScope.launch {
            try {


                googleSignInUseCase()
                    .flowOn(Dispatchers.IO)
                    .collect { _authState.value = it }
            } catch (e: Exception) {
                _loginEvent.send(LoginEvent.ShowSnackBar("Google Sign-In gagal: ${e.message}"))
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _processState.update { it.copy(isLoading = isLoading) }
    }
}


sealed class LoginEvent {
    data object Success : LoginEvent()
    data class ShowSnackBar(val message: String) : LoginEvent()
}
