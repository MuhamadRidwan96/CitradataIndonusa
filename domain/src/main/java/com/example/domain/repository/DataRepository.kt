package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.project.Project
import com.example.domain.response.data.DataResponse
import com.example.domain.response.data.RecordData
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    suspend fun getData(
        page: Int,
        limit: Int
    ): Result<DataResponse<RecordData>>

    suspend fun searchData(
        page: Int,
        limit: Int,
        filters: Map<String, String>
    ): Result<DataResponse<RecordData>>

    fun getDataPaging(
        limit: Int = 10,
        filters: Map<String, String>
    ): Flow<PagingData<Project>>
}