package com.example.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.Result
import com.example.data.pagingSource.DataPagingSource
import com.example.data.remote.api.ApiHelper
import com.example.data.utils.toResult
import com.example.data.utils.toTypedResult
import com.example.domain.repository.DataRepository
import com.example.domain.response.DataResponse
import com.example.domain.response.RecordData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) :
    DataRepository {

    lateinit var onTokenExpiredCallBack: () -> Unit

    override suspend fun getData(page: Int, limit: Int): Flow<Result<DataResponse>> = flow {
        val response = apiHelper.getData(page, limit)
        emit(response.toTypedResult())
    }

    override suspend fun searchData(
        page: Int,
        limit: Int,
        filters: Map<String, String>
    ): Result<DataResponse> {
        val response = apiHelper.searchData(page, limit, filters)
        return response.toResult()
    }


    override fun getDataPaging(
        limit: Int,
        filters: Map<String, String>
    ): Flow<PagingData<RecordData>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                enablePlaceholders = false,
                initialLoadSize = limit * 2,
            ),
            pagingSourceFactory = {
                DataPagingSource(
                    dataRepository = this,
                    limit = limit,
                    onTokenExpired = { onTokenExpiredCallBack.invoke() },
                    filters = filters
                )
            }
        ).flow
    }
}