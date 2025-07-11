package com.example.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.pagingSource.DataPagingSource
import com.example.data.remote.api.ApiHelper
import com.example.data.utils.toResult
import com.example.domain.repository.DataRepository
import com.example.domain.response.DataResponse
import com.example.domain.response.RecordData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.common.Result

class DataRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : DataRepository {
    override suspend fun getData(page: Int, limit: Int): Flow<Result<DataResponse>> = flow {
        val response = apiHelper.getData(page, limit)
        emit(response.toResult())
    }

    override fun getDataPaging(limit: Int): Flow<PagingData<RecordData>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                enablePlaceholders = false,
                initialLoadSize = limit * 2,
            ),
            pagingSourceFactory = {
                DataPagingSource(
                    dataRepository = this,
                    limit = limit
                )
            }
        ).flow
    }
}