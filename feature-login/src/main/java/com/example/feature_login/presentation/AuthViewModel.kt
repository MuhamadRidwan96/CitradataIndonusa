package com.example.feature_login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.response.LoginResponse
import com.example.domain.usecase.LoginUseCase
import com.example.feature_login.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginResult = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val loginResult: StateFlow<UiState<LoginResponse>> = _loginResult

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _isError = MutableSharedFlow<String>()
    val isError: SharedFlow<String> get() = _isError

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = UiState.Idle

            loginUseCase(username, password)
                .map { result ->
                    result.fold(
                        onSuccess = { UiState.Success(it) },
                        onFailure = { UiState.Error(it.localizedMessage ?: "Login failed") }
                    )

                }.catch { exception ->
                    _loginResult.value = UiState.Error(exception.localizedMessage)

                }
                .collectLatest { loginState ->
                    _loginResult.value = loginState
                    if (loginState is UiState.Success) {
                        _isLoggedIn.value = true

                    }
                }
        }
    }


}