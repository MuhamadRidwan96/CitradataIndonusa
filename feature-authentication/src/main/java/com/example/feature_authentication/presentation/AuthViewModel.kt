package com.example.feature_authentication.presentation

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.Constant
import com.example.domain.response.AuthResponse
import com.example.domain.response.LoginResponse
import com.example.domain.usecase.authentication.CheckLoginUseCase
import com.example.domain.usecase.authentication.GoogleSignInUseCase
import com.example.domain.usecase.authentication.LoginUseCase
import com.example.feature_authentication.state.LoginUiState
import com.example.feature_authentication.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkLoginUseCase: CheckLoginUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase
) : ViewModel() {

    private val _loginResult = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val loginResult: StateFlow<UiState<LoginResponse>> = _loginResult

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _authState = MutableStateFlow<AuthResponse?>(null)
    val authState: StateFlow<AuthResponse?> = _authState

    fun onChangeUsername(newUsername: String) {
        _uiState.update {
            it.copy(
                email = newUsername,
                isEmailWrong = !isValidateEmail(newUsername)
            )
        }
    }

    fun onChangePassword(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, isPassWordWrong = newPassword.isBlank()) }
    }

    fun login() {
        if (!validateInput()) return

        viewModelScope.launch {
            setLoading(true)
            loginUseCase(uiState.value.email, uiState.value.password)
                .handleLoginResult()
                .collectLatest { result ->
                    _loginResult.value = result
                    if (result is UiState.Success) {
                        delay(3000)
                        _isLoggedIn.value = true
                    }
                    setLoading(false)
                }
        }
    }

    private fun isValidateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validateInput(): Boolean {
        val state = _uiState.value
        val isEmailValid = isValidateEmail(state.email)
        val isPasswordValid = state.password.isNotBlank()

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

    private fun Flow<Result<LoginResponse>>.handleLoginResult() = map { result ->
        result.fold(
            onSuccess = { UiState.Success(it) },
            onFailure = { UiState.Error(it.localizedMessage ?: "Login failed") }
        )
    }.catch { exception ->
        emit(UiState.Error(exception.localizedMessage ?: "Something went wrong"))
    }

    fun checkLogin() {
        viewModelScope.launch {
            _isLoggedIn.value = checkLoginUseCase()
        }
    }

    fun signWithGoogle(){
        viewModelScope.launch {
            googleSignInUseCase().collect{
                delay(3000)
                _authState.value = it
            }
        }
    }
}