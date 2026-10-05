package com.example.domain.repository

import com.example.domain.model.authentication.LoginModel
import com.example.domain.model.authentication.RegisterModel
import com.example.domain.response.authentication.AuthResponse
import com.example.domain.response.authentication.LoginResponse
import com.example.domain.response.authentication.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
   suspend fun login(requestLogin: LoginModel): Result<LoginResponse>
   suspend fun register(requestRegister:RegisterModel):Result<RegisterResponse>
   fun signWithGoogle():Flow<AuthResponse>
}