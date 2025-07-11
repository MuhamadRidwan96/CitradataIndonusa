package com.example.domain.usecase.data

import androidx.paging.PagingData
import com.example.domain.repository.DataRepository
import com.example.domain.response.RecordData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataUseCase @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(
        limit: Int = 10
    ): Flow<PagingData<RecordData>> {
        return dataRepository.getDataPaging(limit).flowOn(dispatcher)
    }
}