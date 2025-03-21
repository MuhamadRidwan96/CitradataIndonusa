package com.example.data.remote

import com.example.domain.response.LoginResponse
import com.example.domain.response.RegisterResponse
import com.example.domain.model.LoginModel
import com.example.domain.model.RegisterModel
import com.example.domain.response.DataResponse
import com.example.domain.response.ProjectDetailResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper {
    override suspend fun login(requestLogin: LoginModel): Response<LoginResponse> {
        return apiService.login(requestLogin)
    }

    override suspend fun register(requestRegister: RegisterModel): Response<RegisterResponse> {
       return apiService.register(requestRegister)
    }

    override suspend fun searchData(search: Map<String, String>): Response<DataResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getData(): Response<DataResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getDetailData(idProject: String): Response<ProjectDetailResponse> {
        TODO("Not yet implemented")
    }
}