package com.example.data.repositoryImpl

import com.example.common.Result
import com.example.data.remote.api.ApiHelper
import com.example.data.utils.toResult
import com.example.domain.repository.SaveTokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject

class SaveTokenRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper): SaveTokenRepository {
    override fun saveToken(
        userId: String,
        token: String
    ): Flow<Result<ResponseBody>> =  flow {
        val response = apiHelper.saveToken(userId,token)
        emit(response.toResult())
    }
}