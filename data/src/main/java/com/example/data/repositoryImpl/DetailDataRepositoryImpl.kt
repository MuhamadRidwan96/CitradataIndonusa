package com.example.data.repositoryImpl

import com.example.data.network.api.ApiHelper
import com.example.data.utils.toResult
import com.example.domain.repository.DetailDataRepository
import com.example.domain.response.ProjectDetailResponse
import javax.inject.Inject

class DetailDataRepositoryImpl @Inject constructor(val apiHelper: ApiHelper): DetailDataRepository {

    override suspend fun getDetailData(projectId: String): Result<ProjectDetailResponse>{
        return apiHelper.getDetailData(projectId).toResult()
    }
}