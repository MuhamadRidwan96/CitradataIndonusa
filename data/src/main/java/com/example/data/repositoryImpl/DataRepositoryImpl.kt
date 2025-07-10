package com.example.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.pagingSource.DataPagingSource
import com.example.data.remote.api.ApiHelper
import com.example.data.utils.Constant
import com.example.data.utils.ErrorHandle
import com.example.domain.repository.DataRepository
import com.example.domain.response.DataResponse
import com.example.domain.response.RecordData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : DataRepository {
    override fun getData(page: Int, limit: Int): Flow<Result<DataResponse>> = flow {
        try {
            val response = apiHelper.getData(page, limit)
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
                } catch (_: Exception) {
                    Constant.FAILED_PARSE
                }
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
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