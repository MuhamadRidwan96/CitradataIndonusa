package com.example.domain.repository

import com.example.common.Result
import com.example.domain.response.ProjectDetailResponse
import kotlinx.coroutines.flow.Flow

interface DetailDataRepository {
    fun getDetailData(projectId:String): Flow<Result<ProjectDetailResponse>>
}