package com.example.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.network.ApiCallHandler
import com.example.data.network.api.ApiHelper
import com.example.data.pagingSource.FilterPagingSource
import com.example.data.utils.toResult
import com.example.domain.model.filter.FilterDataModel
import com.example.domain.model.project.Project
import com.example.domain.repository.FilterDataRepository
import com.example.domain.utils.toDomain
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class FilterDataRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiCallHandler: ApiCallHandler
) :
    FilterDataRepository {
    lateinit var onTokenExpiredCallBack: () -> Unit
    lateinit var onDataNotFoundCallBack: () -> Unit

    override suspend fun filterData(
        page: Int,
        limit: Int,
        filterData: FilterDataModel?
    ): Result<List<Project>> {
        return apiCallHandler.execute {
            apiHelper.filterData(
                page = page,
                limit = limit,
                filteredData = filterData
            ).toResult()
                .map { response ->
                    response.data.orEmpty()
                        .map { it.toDomain() }
                }
        }
    }

    override fun getFilterDataPaging(
        filterData: FilterDataModel?,
        limit: Int
    ): Flow<PagingData<Project>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                enablePlaceholders = false,
                initialLoadSize = limit * 1
            ),
            pagingSourceFactory = {
                FilterPagingSource(
                    repository = this,
                    filterData = filterData,
                    limit = limit,
                    onDataNotFound = { onDataNotFoundCallBack.invoke() },
                )
            }
        ).flow
    }
}