package com.example.domain.usecase.authentication

import com.example.domain.model.LoginModel
import com.example.domain.model.UserModel
import com.example.domain.preferences.UserPreferences
import com.example.domain.repository.AuthRepository
import com.example.domain.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val userPreferences: UserPreferences
) {
    operator fun invoke(email: String, password: String): Flow<Result<LoginResponse>> =
        repository.login(LoginModel(email, password))
            .onEach { result ->
                if (result.isSuccess) {
                    val loginResponse = result.getOrNull()
                    if (loginResponse != null) {
                        userPreferences.saveSession(
                            UserModel(
                                token = loginResponse.data.token,
                                isLogin = true
                            )
                        )
                    }
                }
            }
            .catch { e ->
                emit(Result.failure(e)) // ✅ Tetap emit agar Flow tidak berhenti
            }
            .flowOn(Dispatchers.IO)
}