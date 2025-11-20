package com.example.domain.usecase.data

import com.example.domain.repository.DetailDataRepository
import com.example.domain.response.ProjectDetailResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.common.Result
import com.example.domain.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn

class DetailDataUseCase @Inject constructor(
    private val detailDataRepository: DetailDataRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher) {

    operator fun invoke(projectId:String): Flow<Result<ProjectDetailResponse>>{
        return detailDataRepository.getDetailData(projectId).flowOn(dispatcher)
    }
}