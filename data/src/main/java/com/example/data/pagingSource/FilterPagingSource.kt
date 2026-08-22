package com.example.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.utils.DataNotFoundException
import com.example.data.utils.TokenExpiredException
import com.example.domain.model.FilterDataModel
import com.example.domain.model.Project
import com.example.domain.repository.FilterDataRepository
import javax.inject.Inject

class FilterPagingSource @Inject constructor(
    private val repository: FilterDataRepository,
    private val filterData: FilterDataModel?,
    private val limit: Int = 10,
    private val onTokenExpired: () -> Unit,
    private val onDataNotFound: () -> Unit
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

            val result = repository.filterData(page, limit, filterData = filterData)

            result.fold(
                onSuccess = { projects ->

                    LoadResult.Page(
                        data = projects,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (projects.isEmpty()){null} else page + 1
                    )
                },
                onFailure = {exception ->
                    if (exception is DataNotFoundException) {
                        onDataNotFound()
                        onTokenExpired()
                    }
                    LoadResult.Error(exception)
                }
            )
        } catch (e: Exception) {
            if(e is TokenExpiredException){
                onTokenExpired
            }
            LoadResult.Error(e)
        }
    }
}

