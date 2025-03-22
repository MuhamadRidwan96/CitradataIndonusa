package com.example.domain.usecase

import com.example.domain.model.LoginModel
import com.example.domain.model.UserModel
import com.example.domain.preferences.UserPreferences
import com.example.domain.repository.Repository
import com.example.domain.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: Repository,
    private val userPreferences: UserPreferences
) {
    operator fun invoke(username: String, password: String): Flow<Result<LoginResponse>> = flow {
        val response = repository.login(LoginModel(username, password)).first()
        if (response.isSuccess) {
            val loginResponse = response.getOrNull()
            if (loginResponse != null) {
                // Simpan sesi setelah login berhasil
                userPreferences.saveSession(
                    UserModel(
                        token = loginResponse.data.token,
                        isLogin = true
                    )
                )
            }
        }
        return@flow
    }
}
