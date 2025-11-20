package com.example.domain.usecase.authentication

import com.example.common.Result
import com.example.domain.di.IoDispatcher
import com.example.domain.model.RegisterModel
import com.example.domain.repository.AuthRepository
import com.example.domain.response.RegisterResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(
        username: String,
        email: String,
        password: String
    ): Flow<Result<RegisterResponse>> {
        return repository.register(RegisterModel(username, email, password))
            .flowOn(dispatcher)
    }

}
