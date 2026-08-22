package com.example.domain.repository


import com.example.domain.response.ProjectDetailResponse

interface DetailDataRepository {
    suspend fun getDetailData(projectId:String): Result<ProjectDetailResponse>
}