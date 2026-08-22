package com.example.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.utils.TokenExpiredException
import com.example.domain.model.Project
import com.example.domain.repository.DataRepository
import com.example.domain.utils.toDomain
import javax.inject.Inject

class DataPagingSource @Inject constructor(
    private val dataRepository: DataRepository,
    private val filters: Map<String, String> = emptyMap(),
    private val limit: Int = 10,
    private val onTokenExpired: () -> Unit
) : PagingSource<Int, Project>() {
    override fun getRefreshKey(state: PagingState<Int, Project>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Project> {
        val page = params.key ?: 1

        return try {

            val result = if (filters.isEmpty()) {
                dataRepository.getData(page, limit)
            } else {
                dataRepository.searchData(page, limit, filters)
            }

            result.fold(
                onSuccess = { response ->
                    val data = response.data.orEmpty()
                        .map { it.toDomain() }
                    LoadResult.Page(
                        data = data,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (data.isEmpty()) null else page + 1
                    )

                },
                onFailure = { exception ->
                    if (exception is TokenExpiredException) {
                        onTokenExpired()
                    }
                    LoadResult.Error(exception)
                })
        } catch (e: Exception) {

            if (e is TokenExpiredException) {
                onTokenExpired()
            }

            LoadResult.Error(e)
        }
    }
}