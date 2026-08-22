package com.example.domain.usecase.data

import androidx.paging.PagingData
import com.example.domain.di.IoDispatcher
import com.example.domain.model.FilterDataModel
import com.example.domain.model.Project
import com.example.domain.repository.FilterDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilteredUseCase @Inject constructor(
    private val filterDataRepository: FilterDataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        limit: Int = 20,
        filterData: FilterDataModel?
    ): Flow<PagingData<Project>> {
        return withContext(dispatcher){   filterDataRepository.getFilterDataPaging(filterData, limit)}
    }
}