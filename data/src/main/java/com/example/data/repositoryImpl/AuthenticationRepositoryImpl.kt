package com.example.data.repositoryImpl

import android.util.Log
import com.example.common.Result
import com.example.data.remote.api.ApiHelper
import com.example.data.remote.google.GoogleAuthManager
import com.example.data.utils.Constant
import com.example.data.utils.ErrorHandle
import com.example.domain.model.LoginModel
import com.example.domain.model.RegisterModel
import com.example.domain.repository.AuthRepository
import com.example.domain.response.AuthResponse
import com.example.domain.response.LoginResponse
import com.example.domain.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val googleAuthManager: GoogleAuthManager
) : AuthRepository {
    override fun login(requestLogin: LoginModel): Flow<Result<LoginResponse>> = flow {

        try {
            val response = apiHelper.login(requestLogin)

            if (response.isSuccessful) {
                response.body()?.let { emit(Result.Success(it)) }
                    ?: emit(Result.Error(Exception(Constant.ERROR_NULL)))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    errorBody?.let { ErrorHandle.parseErrorBody(it) } ?: Constant.UNKNOWN_ERROR
                } catch (_: Exception) {
                    Constant.FAILED_PARSE
                }
                emit(Result.Error(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }


    override fun register(requestRegister: RegisterModel): Flow<Result<RegisterResponse>> = flow {
        Log.d("REPO", "Calling API with: $requestRegister")
        try {
            val response = apiHelper.register(requestRegister)
            if (response.isSuccessful) {
                response.body()?.let { emit(Result.Success(it)) } ?: emit(
                    Result.Error(Exception(Constant.ERROR_NULL))
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    errorBody?.let { ErrorHandle.parseErrorBody(it) } ?: Constant.UNKNOWN_ERROR
                } catch (_: Exception) {
                    Constant.FAILED_PARSE
                }
                emit(Result.Error(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.Error(Exception("Network Error: ${e.localizedMessage}")))
            return@flow
        }
    }

    override fun signWithGoogle(
    ): Flow<AuthResponse> {
        return googleAuthManager.signWithGoogle()
    }
}