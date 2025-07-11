package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.response.DataResponse
import com.example.domain.response.RecordData
import kotlinx.coroutines.flow.Flow
import com.example.common.Result

interface DataRepository {
    suspend fun getData(page: Int, limit: Int): Flow<Result<DataResponse>>
    fun getDataPaging(limit : Int = 10): Flow<PagingData<RecordData>>
}