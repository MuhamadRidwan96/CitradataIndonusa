package com.example.domain.repository

import com.example.domain.model.LoginModel
import com.example.domain.model.RegisterModel
import com.example.domain.response.AuthResponse
import com.example.domain.response.LoginResponse
import com.example.domain.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
   suspend fun login(requestLogin: LoginModel): Result<LoginResponse>
   suspend fun register(requestRegister:RegisterModel):Result<RegisterResponse>
   fun signWithGoogle():Flow<AuthResponse>
}