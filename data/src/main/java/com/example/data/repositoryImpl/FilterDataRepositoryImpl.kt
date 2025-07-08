package com.example.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.pagingSource.DataPagingSource
import com.example.data.remote.api.ApiHelper
import com.example.data.utils.Constant
import com.example.data.utils.ErrorHandle
import com.example.domain.model.FilterDataModel
import com.example.domain.repository.FilterDataRepository
import com.example.domain.response.DataResponse
import com.example.domain.response.RecordData
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FilterDataRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper
) : FilterDataRepository {
    override fun filterData(
        page: Int,
        limit: Int,
        filterData: FilterDataModel?
    ): Flow<Result<DataResponse>> = flow {
        try {
            val response = apiHelper.filterData(page, limit, filterData)

            if (response.isSuccessful) {
                response.body()?.let { emit(Result.success(it)) } ?: emit(
                    Result.failure(
                        Exception(
                            Constant.ERROR_NULL
                        )
                    )
                )

            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    errorBody?.let { ErrorHandle.parseErrorBody(it) } ?: Constant.UNKNOWN_ERROR
                } catch (_: Exception) { Constant.FAILED_PARSE }
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: Exception) { emit(Result.failure(e)) }
    }.flowOn(Dispatchers.IO)

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
                DataPagingSource(
                    repository = this,
                    filterData = filterData,
                    limit = limit
                )
            }
        ).flow
    }
}