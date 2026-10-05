package com.example.domain.repository


import com.example.domain.response.data.ProjectDetailResponse

interface DetailDataRepository {
    suspend fun getDetailData(projectId:String): Result<ProjectDetailResponse>
}