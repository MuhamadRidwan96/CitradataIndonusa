package com.example.domain.repository


import com.example.domain.model.LoginModel
import com.example.domain.model.RegisterModel
import com.example.domain.response.AuthResponse
import com.example.domain.response.LoginResponse
import com.example.domain.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import com.example.common.Result

interface AuthRepository {
   fun login(requestLogin: LoginModel): Flow<Result<LoginResponse>>
   fun register(requestRegister:RegisterModel):Flow<Result<RegisterResponse>>
   fun signWithGoogle():Flow<AuthResponse>
}