package com.example.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.Result
import com.example.data.pagingSource.FilterPagingSource
import com.example.data.remote.api.ApiHelper
import com.example.data.utils.toTypedResult
import com.example.domain.model.FilterDataModel
import com.example.domain.repository.FilterDataRepository
import com.example.domain.response.DataResponse
import com.example.domain.response.RecordData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FilterDataRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) :
    FilterDataRepository {
    lateinit var onTokenExpiredCallBack: () -> Unit

    override fun filterData(
        page: Int,
        limit: Int,
        filterData: FilterDataModel?
    ): Flow<Result<DataResponse>> = flow {
        val response = apiHelper.filterData(
            page = page,
            limit = limit,
            filteredData = filterData
        )
        emit(response.toTypedResult())
    }

    override fun getFilterDataPaging(
        filterData: FilterDataModel?,
        limit: Int
    ): Flow<PagingData<RecordData>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                enablePlaceholders = false,
                initialLoadSize = limit * 2
            ),
            pagingSourceFactory = {
                FilterPagingSource(
                    repository = this,
                    filterData = filterData,
                    limit = limit,
                    onTokenExpired = { onTokenExpiredCallBack.invoke() },
                )
            }
        ).flow
    }
}