package com.example.domain.usecase.authentication

import com.example.domain.di.IoDispatcher
import com.example.domain.model.RegisterModel
import com.example.domain.repository.AuthRepository
import com.example.domain.response.RegisterResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String
    ): Result<RegisterResponse> {
        return withContext(dispatcher) {
            repository.register(RegisterModel(username, email, password))
        }
    }
}
