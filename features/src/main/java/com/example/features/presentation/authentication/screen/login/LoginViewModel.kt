package com.example.features.presentation.authentication.screen.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.domain.response.AuthResponse
import com.example.domain.usecase.authentication.CheckLoginUseCase
import com.example.domain.usecase.authentication.GoogleSignInUseCase
import com.example.domain.usecase.authentication.LoginUseCase
import com.example.features.presentation.authentication.state.LoginFormState
import com.example.features.presentation.authentication.state.LoginProcessState
import com.example.features.presentation.authentication.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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

    private val _loginEvent = Channel<LoginEvent>()
    val loginEvent = _loginEvent.receiveAsFlow()

    private val _formState = MutableStateFlow(LoginFormState())
    val formState = _formState.asStateFlow()

    private val _processState = MutableStateFlow(LoginProcessState())
    val processState = _processState.asStateFlow()

    private val _authState = MutableStateFlow<AuthResponse?>(null)
    val authState = _authState.asStateFlow()

    val isSubmitEnabled: StateFlow<Boolean> = combine(
        formState.map { it.email },
        formState.map { it.password }
    ) { email, password ->
        isValidEmail(email) && isValidPassword(password)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
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
            _processState.update { it.copy(isLoading = true) }

            try {
                loginUseCase(_formState.value.email, _formState.value.password)
                    .flowOn(Dispatchers.IO)
                    .toUiState()
                    .collectLatest { result ->
                        when (result) {
                            is UiState.Success -> {
                                _processState.update {
                                    it.copy(
                                        isLoading = false,
                                        isLoggedIn = true
                                    )
                                }
                                _loginEvent.send(LoginEvent.Success)
                            }

                            is UiState.Error -> {
                                _formState.update {
                                    it.copy(errorMessage = result.message)
                                }
                                _loginEvent.send(LoginEvent.ShowSnackBar(result.message))
                            }

                            else -> Unit
                        }
                    }
            } catch (e: Exception) {
                _loginEvent.send(LoginEvent.ShowSnackBar("Terjadi kesalahan : ${e.message}"))

            } finally {
                _processState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun <T> Flow<Result<T>>.toUiState(): Flow<UiState<T>> = map { result ->
        when (result) {
            is Result.Success -> UiState.Success(result.data)
            is Result.Error -> UiState.Error(result.exception.message ?: "Unknown error")
            is Result.Loading -> UiState.Loading
        }
    }

    fun checkLogin() {
        viewModelScope.launch {
            val isLoggedIn = withContext(Dispatchers.IO) { checkLoginUseCase() }
            _processState.update {
                it.copy(
                    isLoggedIn = isLoggedIn,
                    isReady = true
                )
            }
        }
    }

    val signWithGoogle: () -> Unit = {
        viewModelScope.launch {
            googleSignInUseCase()
                .flowOn(Dispatchers.IO)
                .collect {
                    _authState.value = it
                }
        }
    }
}

sealed class LoginEvent {
    data object Success : LoginEvent()
    data class ShowSnackBar(val message: String) : LoginEvent()
}
