package com.example.data.remote

import com.example.data.response.LoginResponse
import com.example.data.response.RegisterResponse
import com.example.domain.model.LoginModel
import com.example.domain.model.RegisterModel
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper {
    override suspend fun login(requestLogin: LoginModel): Response<LoginResponse> {
        return apiService.login(requestLogin)
    }

    override suspend fun register(requestRegister: RegisterModel): Response<RegisterResponse> {
        TODO("Not yet implemented")
    }
}