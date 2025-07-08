package com.example.domain.usecase.authentication

import android.util.Log
import com.example.common.Result
import com.example.domain.model.RegisterModel
import com.example.domain.repository.AuthRepository
import com.example.domain.response.RegisterResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(
        username: String,
        email: String,
        password: String
    ): Flow<Result<RegisterResponse>> {
        Log.d("USE CASE", "RegisterUseCase invoked: $username, $email, $password")
        return repository.register(RegisterModel(username, email, password))
            .flowOn(dispatcher)
    }

}
