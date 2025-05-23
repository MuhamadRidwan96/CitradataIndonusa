package com.example.features.presentation.authentication

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.Constant
import com.example.domain.response.AuthResponse
import com.example.domain.response.LoginResponse
import com.example.domain.usecase.authentication.CheckLoginUseCase
import com.example.domain.usecase.authentication.GoogleSignInUseCase
import com.example.domain.usecase.authentication.LoginUseCase
import com.example.features.presentation.authentication.state.LoginUiState
import com.example.features.presentation.authentication.state.SignUpFormState
import com.example.features.presentation.authentication.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkLoginUseCase: CheckLoginUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase
) : ViewModel() {

    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent: SharedFlow<LoginEvent> = _loginEvent

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _authState = MutableStateFlow<AuthResponse?>(null)
    val authState = _authState.asStateFlow()

    private val _uiStates = MutableStateFlow(SignUpFormState())
    val uiStates: StateFlow<SignUpFormState> = _uiStates

    fun onChangeEmail(newEmail: String) {
        _uiState.update {
            it.copy(
                email = newEmail,
                isEmailWrong = !isValidEmail(newEmail)
            )
        }
    }

    fun onChangePassword(newPassword: String) {
        _uiState.update {
            it.copy(
                password = newPassword,
                isPassWordWrong = !isValidPassword(newPassword)
            )
        }
    }

    fun login() {
        if (!validateInput()) return

        viewModelScope.launch {
            setLoading(true)
            loginUseCase(uiState.value.email, uiState.value.password)
                .toUiState()
                .collectLatest { result ->
                    when (result) {
                        is UiState.Success -> {
                            _loginEvent.emit(LoginEvent.Success)
                            delay(3000)
                            _isLoggedIn.value = true
                        }
                        is UiState.Error -> {
                            _loginEvent.emit(LoginEvent.Error(result.message))
                        }
                        else -> Unit
                    }

                    setLoading(false)
                }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun validateInput(): Boolean {
        val state = _uiState.value
        val isEmailValid = isValidEmail(state.email)
        val isPasswordValid = isValidPassword(state.password)

        val isValid = isEmailValid && isPasswordValid

        if (!isValid) {
            _uiState.update {
                it.copy(
                    isEmailWrong = !isEmailValid,
                    isPassWordWrong = !isPasswordValid,
                    errorMessage = Constant.TEXT_FIELD_MESSAGE
                )
            }
        }
        return isValid
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    private fun Flow<Result<LoginResponse>>.toUiState(): Flow<UiState<LoginResponse>> =
        map { result ->
            result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.localizedMessage ?: "Login failed") }
            )
        }.catch { exception ->
            emit(UiState.Error(exception.localizedMessage ?: "Something went wrong"))
        }


    fun checkLogin() {
        viewModelScope.launch {
            _isLoggedIn.value = withContext(Dispatchers.IO) {
                checkLoginUseCase()
            }
        }
    }

    fun signWithGoogle() {
        viewModelScope.launch {
            googleSignInUseCase().collect {
                _authState.value = it
            }
        }
    }
}

sealed class LoginEvent {
    data object Success : LoginEvent()
    data class Error(val message: String) : LoginEvent()
}