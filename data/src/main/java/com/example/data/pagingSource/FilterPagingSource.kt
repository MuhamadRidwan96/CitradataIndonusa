package com.example.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.common.Result
import com.example.data.utils.Constant
import com.example.data.utils.TokenExpiredException
import com.example.domain.model.FilterDataModel
import com.example.domain.repository.FilterDataRepository
import com.example.domain.response.RecordData
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class FilterPagingSource @Inject constructor(
    private val repository: FilterDataRepository,
    private val filterData: FilterDataModel?,
    private val limit: Int = 10,
    private val onTokenExpired: () -> Unit
) : PagingSource<Int, RecordData>() {
    override fun getRefreshKey(state: PagingState<Int, RecordData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecordData> {
        val page = params.key ?: 1
        return try {

            val results = repository.filterData(page, limit, filterData)
            val result = results.firstOrNull()
                ?: return LoadResult.Error(Exception("No data received from repository"))

            when (result) {
                is Result.Success -> {
                    val responseData = result.data
                    val data = responseData.data ?: emptyList()

                    // Determine next key based on whether there are more items
                    val nextPage = if (data.size < limit || data.isEmpty()) {
                        null
                    } else {
                        page + 1
                    }
                    LoadResult.Page(
                        data = data,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = nextPage
                    )
                }

                is Result.Error -> {
                   handleError(result.exception as Exception)
                }

                is Result.Loading -> {
                    LoadResult.Error(Exception(Constant.UNKNOWN_ERROR))
                }
            }
        } catch (e: Exception) {
            if (e is TokenExpiredException) {
                onTokenExpired() // kirim sinyal ke viewmodel
            }
            LoadResult.Error(e)

        }
    }

    private fun handleError(exception: Exception): LoadResult.Error<Int, RecordData> {
        return when (exception) {
            is TokenExpiredException -> {
                onTokenExpired()
                LoadResult.Error(exception) }
            else -> {
                LoadResult.Error(exception)
            }
        }
    }
}
