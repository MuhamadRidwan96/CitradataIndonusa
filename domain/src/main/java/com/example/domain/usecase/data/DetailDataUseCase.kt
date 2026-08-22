package com.example.domain.usecase.data

import com.example.domain.di.IoDispatcher
import com.example.domain.repository.DetailDataRepository
import com.example.domain.response.ProjectDetailResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailDataUseCase @Inject constructor(
    private val detailDataRepository: DetailDataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(projectId: String): Result<ProjectDetailResponse> {
        return withContext(dispatcher) {
            detailDataRepository.getDetailData(projectId)
        }
    }
}