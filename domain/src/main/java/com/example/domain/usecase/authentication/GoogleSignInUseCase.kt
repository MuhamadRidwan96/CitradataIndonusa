package com.example.domain.usecase.authentication

import com.example.domain.repository.AuthRepository
import com.example.domain.response.AuthResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<AuthResponse> = repository.signWithGoogle()
}