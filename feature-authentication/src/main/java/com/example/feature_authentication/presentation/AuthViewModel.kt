package com.example.feature_authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.Constant
import com.example.domain.response.LoginResponse
import com.example.domain.usecase.CheckLoginUseCase
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.LogoutUseCase
import com.example.feature_authentication.state.LoginUiState
import com.example.feature_authentication.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
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

) : ViewModel() {

    private val _loginResult = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val loginResult: StateFlow<UiState<LoginResponse>> = _loginResult

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun onChangeUsername(newUsername: String) {
        _uiState.update { it.copy(username = newUsername, isUserNameWrong = newUsername.isBlank()) }
    }

    fun onChangePassword(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, isPassWordWrong = newPassword.isBlank()) }
    }

    fun login() {
        if (!validateInput()) return

        viewModelScope.launch {
            setLoading(true)
            loginUseCase(uiState.value.username, uiState.value.password)
                .handleLoginResult()
                .collectLatest { result ->
                    _loginResult.value = result
                    if (result is UiState.Success) {
                        _isLoggedIn.value = true
                    }
                    setLoading(false)
                }
        }
    }

    private fun validateInput(): Boolean {
        val state = _uiState.value
        val isValid = state.username.isNotBlank() && state.password.isNotBlank()

        if (!isValid) {
            _uiState.update {
                it.copy(
                    isUserNameWrong = state.username.isBlank(),
                    isPassWordWrong = state.password.isBlank(),
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



    fun checkLogin(){
        viewModelScope.launch {
            _isLoggedIn.value = checkLoginUseCase()
        }
    }
}