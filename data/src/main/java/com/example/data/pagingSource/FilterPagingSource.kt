package com.example.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.utils.Constant
import com.example.domain.model.FilterDataModel
import com.example.domain.repository.FilterDataRepository
import com.example.domain.response.RecordData
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FilterPagingSource @Inject constructor(
    private val repository: FilterDataRepository,
    private val filterData: FilterDataModel?,
    private val limit: Int = 10
) : PagingSource<Int, RecordData>() {
    override fun getRefreshKey(state: PagingState<Int, RecordData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecordData> {
        val page = params.key ?:1
        return try {

            val result = repository.filterData(page,limit,filterData).first()

            if (result.isSuccess) {
                val data = result.getOrNull()?.data ?: emptyList()
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (data.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(result.exceptionOrNull() ?: Exception(Constant.UNKNOWN_ERROR))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}