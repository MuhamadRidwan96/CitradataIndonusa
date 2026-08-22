package com.example.domain.usecase.data

import androidx.paging.PagingData
import com.example.domain.di.IoDispatcher
import com.example.domain.model.Project
import com.example.domain.repository.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataUseCase @Inject constructor(
    private val dataRepository: DataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(
        limit: Int = 10,
        filters : Map<String, String>
    ): Flow<PagingData<Project>> {
        return dataRepository.getDataPaging(limit, filters).flowOn(dispatcher)
    }
}