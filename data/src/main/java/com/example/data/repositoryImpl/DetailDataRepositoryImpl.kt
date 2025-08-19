package com.example.data.repositoryImpl

import com.example.common.Result
import com.example.data.remote.api.ApiHelper
import com.example.data.utils.toResult
import com.example.domain.repository.DetailDataRepository
import com.example.domain.response.ProjectDetailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailDataRepositoryImpl @Inject constructor(val apiHelper: ApiHelper): DetailDataRepository {

    override fun getDetailData(projectId: String): Flow<Result<ProjectDetailResponse>> = flow {
        val response = apiHelper.getDetailData(projectId)
        emit(response.toResult())
    }
}