package com.example.data.repositoryImpl

import com.example.common.Result
import com.example.data.remote.api.ApiHelper
import com.example.data.utils.toResult
import com.example.domain.repository.ProfileRepository
import com.example.domain.response.ProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : ProfileRepository {
    override suspend fun getUser(): Flow<Result<ProfileResponse>> = flow {
        val response = apiHelper.getUser()
        emit(response.toResult())
    }
}