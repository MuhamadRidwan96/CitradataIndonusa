package com.example.domain.repository

import androidx.paging.PagingData
import com.example.common.Result
import com.example.domain.response.DataResponse
import com.example.domain.response.RecordData
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun getData(page: Int, limit: Int): Flow<Result<DataResponse>>
    suspend fun searchData(page:Int, limit:Int, filters:Map<String, String>) : Result<DataResponse>
    fun getDataPaging(limit : Int = 10, filters:Map<String, String>): Flow<PagingData<RecordData>>

}