package com.example.data.remote

import com.example.data.response.LoginResponse
import com.example.data.response.RegisterResponse
import com.example.domain.model.LoginModel
import com.example.domain.model.RegisterModel
import retrofit2.Response

interface ApiHelper{
    suspend fun login(requestLogin:LoginModel):Response<LoginResponse>
    suspend fun register(requestRegister: RegisterModel):Response<RegisterResponse>
}

