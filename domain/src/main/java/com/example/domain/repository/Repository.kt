package com.example.domain.repository


import com.example.domain.model.LoginModel
import com.example.domain.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
   fun login(requestLogin: LoginModel): Flow<Result<LoginResponse>>
}