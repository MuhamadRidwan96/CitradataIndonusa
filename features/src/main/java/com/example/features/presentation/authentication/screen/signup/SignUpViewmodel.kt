package com.example.features.presentation.authentication.screen.signup

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.core_ui.architecture.action.ActionHandler
import com.example.core_ui.architecture.base.BaseViewModel
import com.example.domain.usecase.authentication.RegisterUseCase
import com.example.features.presentation.authentication.state.signup.SignUpUiAction
import com.example.features.presentation.authentication.state.signup.SignUpUiEvent
import com.example.features.presentation.authentication.state.signup.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewmodel @Inject constructor(
    private val signUpUseCase: RegisterUseCase,
) : BaseViewModel<
        SignUpUiState,
        SignUpUiEvent
        >(initialState = SignUpUiState()), ActionHandler<SignUpUiAction> {


    override fun action(action: SignUpUiAction) {
        when (action) {
            is SignUpUiAction.SignUp -> {
                signUp()
            }

            is SignUpUiAction.UsernameChanged -> {
                reduce {
                    copy(
                        username = action.username,
                        isUsernameWrong = !isUsernameValid(action.username),
                        errorMessage = null
                    )
                }
            }

            is SignUpUiAction.EmailChanged -> {
                reduce {
                    copy(
                        email = action.email,
                        isEmailWrong = !isValidEmail(action.email),
                        errorMessage = null
                    )
                }
            }

            is SignUpUiAction.PasswordChanged -> {
                reduce {
                    copy(
                        password = action.password,
                        isPasswordWrong = !isValidPassword(action.password),
                        errorMessage = null
                    )
                }
            }
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

    fun signUp() {
        val state = uiState.value

        viewModelScope.launch {

            signUpUseCase(
                username = state.username,
                email = state.email,
                password = state.password
            ).fold(
                onSuccess = {
                    sendEvent(SignUpUiEvent.Success)
                },
                onFailure = { exception ->
                    val message = exception.message ?: "Failed on Sign Up"

                    reduce {
                        copy(
                            errorMessage = message
                        )
                    }

                    sendEvent(
                        SignUpUiEvent.ShowSnackBar(message)
                    )

                }
            )
        }
    }
}