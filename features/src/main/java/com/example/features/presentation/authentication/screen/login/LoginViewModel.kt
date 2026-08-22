package com.example.features.presentation.authentication.screen.login

import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.core_ui.architecture.action.ActionHandler
import com.example.core_ui.architecture.base.BaseViewModel
import com.example.domain.di.IoDispatcher
import com.example.domain.preferences.UserPreferences
import com.example.domain.response.AuthResponse
import com.example.domain.usecase.authentication.CheckLoginUseCase
import com.example.domain.usecase.authentication.GoogleSignInUseCase
import com.example.domain.usecase.authentication.LoginUseCase
import com.example.domain.usecase.authentication.SaveTokenUseCase
import com.example.features.presentation.authentication.state.login.LoginUiAction
import com.example.features.presentation.authentication.state.login.LoginUiEvent
import com.example.features.presentation.authentication.state.login.LoginUiState
import com.example.features.presentation.authentication.utils.isValidEmail
import com.example.features.presentation.authentication.utils.isValidPassword
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkLoginUseCase: CheckLoginUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val userPreferences: UserPreferences,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel<
        LoginUiState,
        LoginUiEvent
        >
    (initialState = LoginUiState()),
    ActionHandler<LoginUiAction> {

    private val _authState = MutableStateFlow<AuthResponse?>(null)
    val authState = _authState.asStateFlow()

    override fun action(action: LoginUiAction) {
        when (action) {
            is LoginUiAction.EmailChanged -> {
                reduce {
                    copy(
                        email = action.email,
                        isEmailWrong = !isValidEmail(action.email),
                        errorMessage = null
                    )
                }
            }

            is LoginUiAction.PasswordChanged -> {
                reduce {
                    copy(
                        password = action.password,
                        isPassWordWrong = !isValidPassword(action.password),
                        errorMessage = null
                    )
                }
            }

            is LoginUiAction.SignInWithGoogle -> {
                signWithGoogle()
            }

            is LoginUiAction.Login -> {
                login()
            }
        }
    }


    private fun login() {
        val state = uiState.value

        viewModelScope.launch {

            loginUseCase(state.email, state.password)
                .fold(
                    onSuccess = {
                        val session = userPreferences.getSession().first()

                        if (session.idUser.isNotBlank()) {
                            syncFcmToken(session.idUser)
                        } else {
                            Timber.tag("AuthViewModel").w("⚠️ userId tidak ditemukan di JWT")
                        }
                        reduce {
                            copy(
                                isLoggedIn = true
                            )
                        }
                        sendEvent(LoginUiEvent.Success)

                    },
                    onFailure = { exception ->
                        val message = exception.toLoginMessage()

                        reduce {
                            copy(
                                errorMessage = message
                            )
                        }
                        sendEvent(LoginUiEvent.ShowSnackBar(message))
                    }
                )
        }
    }


    fun checkLogin() {
        viewModelScope.launch {
            val isLoggedIn = checkLoginUseCase()

            reduce {
                copy(
                    isLoggedIn = isLoggedIn,
                    isReady = true
                )
            }
        }
    }

    fun signWithGoogle() {
        viewModelScope.launch {
            googleSignInUseCase()
                .flowOn(dispatcher)
                .collect { _authState.value = it }

        }
    }

    private fun syncFcmToken(userId: String) {
        viewModelScope.launch {
            val fcmToken = FirebaseMessaging.getInstance().token.await()
            saveTokenUseCase(userId, fcmToken)

        }
    }
}

private fun Throwable.toLoginMessage(): String {
    return when (this) {
        is SocketTimeoutException ->
            "Koneksi ke server timeout. Silakan coba lagi."

        is UnknownHostException ->
            "Tidak ada koneksi internet."

        is ConnectException ->
            "Tidak dapat terhubung ke server."

        is HttpException ->
            "Terjadi kesalahan pada server."

        else ->
            "Login gagal. Silakan coba lagi."
    }
}
