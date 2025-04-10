package com.example.data.repositoryImpl

import com.example.data.remote.ApiHelper
import com.example.data.utils.Constant
import com.example.data.utils.ErrorHandle
import com.example.domain.model.LoginModel
import com.example.domain.model.RegisterModel
import com.example.domain.repository.Repository
import com.example.domain.response.LoginResponse
import com.example.domain.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : Repository {
    override fun login(requestLogin: LoginModel): Flow<Result<LoginResponse>> = flow {

        try {
            val response = apiHelper.login(requestLogin)

            if (response.isSuccessful) {
                response.body()?.let { emit(Result.success(it)) }
                    ?: emit(Result.failure(Exception(Constant.ERROR_NULL)))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    errorBody?.let { ErrorHandle.parseErrorBody(it) } ?: Constant.UNKNOWN_ERROR
                } catch (e: Exception) {
                    Constant.FAILED_PARSE
                }
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)


    override fun register(requestRegister: RegisterModel): Flow<Result<RegisterResponse>> = flow {
        val response = try {
            apiHelper.register(requestRegister)
        } catch (e: Exception) {
            emit(Result.failure(Exception("Network Error: ${e.localizedMessage}")))
            return@flow
        }
        if (response.isSuccessful) {
            response.body()?.let { emit(Result.success(it)) } ?: emit(
                Result.failure(Exception(Constant.ERROR_NULL))
            )
        } else {
            val errorBody = response.errorBody()?.string()
            val errorMessage = try {
                errorBody?.let { ErrorHandle.parseErrorBody(it) } ?: Constant.UNKNOWN_ERROR
            } catch (e: Exception) {
                Constant.FAILED_PARSE
            }
            emit(Result.failure(Exception(errorMessage)))
        }
    }
}