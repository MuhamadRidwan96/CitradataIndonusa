package com.example.domain.usecase.data

import androidx.paging.PagingData
import com.example.domain.model.FilterDataModel
import com.example.domain.repository.FilterDataRepository
import com.example.domain.response.RecordData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FilteredUseCase @Inject constructor(
    private val filterDataRepository: FilterDataRepository,
    private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(
        limit: Int = 10,
        filterData: FilterDataModel?
    ): Flow<PagingData<RecordData>> {
        return filterDataRepository.getFilterDataPaging(filterData, limit).flowOn(dispatcher)
    }
}