package com.example.data.repositoryImpl


import com.example.data.remote.api.ApiHelper
import com.example.data.remote.google.GoogleAuthManager
import com.example.data.utils.toResult
import com.example.domain.model.LoginModel
import com.example.domain.model.RegisterModel
import com.example.domain.repository.AuthRepository
import com.example.domain.response.AuthResponse
import com.example.domain.response.LoginResponse
import com.example.domain.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.common.Result

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val googleAuthManager: GoogleAuthManager
) : AuthRepository {
    override fun login(requestLogin: LoginModel): Flow<Result<LoginResponse>> = flow {

        val response = apiHelper.login(requestLogin)
        emit(response.toResult())
    }

    override fun register(requestRegister: RegisterModel): Flow<Result<RegisterResponse>> = flow {

        val response = apiHelper.register(requestRegister)
        emit(response.toResult())
    }

    override fun signWithGoogle(
    ): Flow<AuthResponse> {
        return googleAuthManager.signWithGoogle()
    }
}