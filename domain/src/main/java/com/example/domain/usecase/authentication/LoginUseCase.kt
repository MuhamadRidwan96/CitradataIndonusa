package com.example.domain.usecase.authentication

import com.example.common.Result
import com.example.domain.model.LoginModel
import com.example.domain.model.UserModel
import com.example.domain.preferences.UserPreferences
import com.example.domain.repository.AuthRepository
import com.example.domain.response.LoginResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val userPreferences: UserPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(email: String, password: String): Flow<Result<LoginResponse>> =
        repository.login(LoginModel(email, password))
            .onEach { result ->
                when (result) {
                    is Result.Success -> {
                        val loginResponse = result.data
                        userPreferences.saveSession(
                            UserModel(
                                token = loginResponse.data.token,
                                isLogin = true
                            )
                        )
                    }
                    else -> Unit
                }
            }
            .catch { e ->
                emit(Result.Error(e))
            }
            .flowOn(dispatcher)

}