package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.FilterDataModel
import com.example.domain.repository.FilterDataRepository
import com.example.domain.response.RecordData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterDataUseCase @Inject constructor(private val repository: FilterDataRepository) {
    operator fun invoke(
        filterData: FilterDataModel? = null,
        limit: Int = 10
    ): Flow<PagingData<RecordData>> {
        return repository.getFilterDataPaging(filterData, limit)
    }
}