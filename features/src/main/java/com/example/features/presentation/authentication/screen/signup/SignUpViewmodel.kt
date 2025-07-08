package com.example.features.presentation.authentication.screen.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.domain.usecase.authentication.RegisterUseCase
import com.example.features.presentation.authentication.state.SignUpFormState
import com.example.features.presentation.authentication.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewmodel @Inject constructor(
    private val signUpUseCase: RegisterUseCase
) : ViewModel() {

    private val _signUpEvent = Channel<SignUpEvent>()
    val signUpEvent = _signUpEvent.receiveAsFlow()

    private val _formState = MutableStateFlow(SignUpFormState())
    val formState = _formState.asStateFlow()

    val onChangeUsername: (String) -> Unit = { newUsername ->
        _formState.update {
            it.copy(username = newUsername, isUsernameWrong = !isUsernameValid(newUsername), errorMessage = null)
        }
    }

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
                isPasswordWrong = newPassword.isNotEmpty() && !isValidPassword(newPassword),
                errorMessage = null
            )
        }
    }

    private fun isUsernameValid(username: String): Boolean {
        val valid = username.isNotEmpty()
        return valid
    }

    private fun isValidEmail(email: String): Boolean {
        val match = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val valid = email.isNotEmpty() && match
        return valid
    }

    private fun isValidPassword(password: String): Boolean {
        val valid = password.length >= 6
        return valid
    }

    fun <T> Flow<Result<T>>.toUiState(): Flow<UiState<T>> = map { result ->
        when (result) {
            is Result.Success -> UiState.Success(result.data)
            is Result.Error -> UiState.Error(result.exception.message ?: "Unknown error")
            is Result.Loading -> UiState.Loading
        }
    }


    val signUp: () -> Unit = signUp@{

        viewModelScope.launch {
            try {
                signUpUseCase(
                    _formState.value.username,
                    _formState.value.email,
                    _formState.value.password
                )
                    .flowOn(Dispatchers.IO)
                    .toUiState()
                    .collectLatest { result ->
                        when (result) {
                            is UiState.Success -> {
                                _signUpEvent.send(SignUpEvent.Success)
                            }

                            is UiState.Error -> {
                                _formState.update {
                                    it.copy(errorMessage = result.message)
                                }
                                _signUpEvent.send(SignUpEvent.ShowSnackBar(result.message))

                            }

                            else -> Unit
                        }
                    }

            } catch (e: Exception) {
                _signUpEvent.send(SignUpEvent.ShowSnackBar("Terjadi kesalahan : ${e.message}"))
            }
        }
    }
}


sealed class SignUpEvent {
    data object Success : SignUpEvent()
    data class ShowSnackBar(val message: String) : SignUpEvent()
}