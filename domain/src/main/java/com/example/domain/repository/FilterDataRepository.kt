package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.FilterDataModel
import com.example.domain.response.DataResponse
import com.example.domain.response.RecordData
import kotlinx.coroutines.flow.Flow
import com.example.common.Result

interface FilterDataRepository {
    fun filterData(page:Int,limit: Int, filterData: FilterDataModel?): Flow<Result<DataResponse>>
    fun getFilterDataPaging(filterData: FilterDataModel?, limit: Int = 10): Flow<PagingData<RecordData>>
}