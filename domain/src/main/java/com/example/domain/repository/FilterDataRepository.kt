package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.FilterDataModel
import com.example.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface FilterDataRepository {
    suspend fun filterData(page:Int,limit: Int, filterData: FilterDataModel?): Result<List<Project>>
    fun getFilterDataPaging(filterData: FilterDataModel?, limit: Int = 10): Flow<PagingData<Project>>
}