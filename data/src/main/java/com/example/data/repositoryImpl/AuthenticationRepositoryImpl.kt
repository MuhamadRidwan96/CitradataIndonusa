package com.example.data.repositoryImpl


import com.example.data.network.api.ApiHelper
import com.example.data.network.google.GoogleAuthManager
import com.example.data.utils.toResult
import com.example.domain.model.authentication.LoginModel
import com.example.domain.model.authentication.RegisterModel
import com.example.domain.repository.AuthRepository
import com.example.domain.response.authentication.AuthResponse
import com.example.domain.response.authentication.LoginResponse
import com.example.domain.response.authentication.RegisterResponse
import kotlinx.coroutines.flow.Flow
import okio.IOException
import timber.log.Timber
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val googleAuthManager: GoogleAuthManager
) : AuthRepository {
    override suspend fun login(requestLogin: LoginModel): Result<LoginResponse> {
        return try {
            Timber.tag("AuthRepository").d("LOGIN REQUEST START")
            apiHelper.login(requestLogin).toResult()
        } catch (e: IOException) {
            Timber.tag("AuthRepository").d("LOGIN NETWORK ERROR")
            Result.failure(e)
        }
    }

    override suspend fun register(requestRegister: RegisterModel): Result<RegisterResponse> {

        return apiHelper.register(requestRegister).toResult()
    }

    override fun signWithGoogle(
    ): Flow<AuthResponse> {
        return googleAuthManager.signWithGoogle()
    }
}