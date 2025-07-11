package com.example.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.common.Result
import com.example.data.utils.Constant
import com.example.domain.repository.DataRepository
import com.example.domain.response.RecordData
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataPagingSource @Inject constructor(
    private val dataRepository: DataRepository,
    private val limit: Int = 10
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
            val result = dataRepository.getData(page, limit).first()

            when (result) {
                is Result.Success -> {
                    val data = result.data.data ?: emptyList()
                    LoadResult.Page(
                        data = data,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (data.isEmpty()) null else page + 1
                    )
                }

                is Result.Error -> {
                    LoadResult.Error(result.exception)
                }

                is Result.Loading -> {
                    // Ini sebenarnya tidak relevan di PagingSource, tapi harus di-handle
                    LoadResult.Error(Exception(Constant.UNKNOWN_ERROR))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}