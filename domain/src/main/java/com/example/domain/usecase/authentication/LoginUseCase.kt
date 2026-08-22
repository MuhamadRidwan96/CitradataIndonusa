package com.example.domain.usecase.authentication

import com.example.domain.di.IoDispatcher
import com.example.domain.model.LoginModel
import com.example.domain.model.UserModel
import com.example.domain.preferences.UserPreferences
import com.example.domain.repository.AuthRepository
import com.example.domain.response.LoginResponse
import com.example.domain.utils.decodeJWTPayload
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val userPreferences: UserPreferences,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(email: String, password: String): Result<LoginResponse> {


        return withContext(dispatcher) {
            repository.login(LoginModel(email, password))
                .onSuccess { result ->
                    val loginResponse = result.data
                    val token = loginResponse.token
                    // decode JWT di sini supaya idUser langsung tersedia
                    val payload = decodeJWTPayload(token)
                    val userId = payload?.optString("iduser", "") ?: ""
                    userPreferences.saveSession(
                        UserModel(
                            token = loginResponse.token,
                            isLogin = true,
                            idUser = userId
                        )
                    )
                }
        }
    }
}