package com.example.domain.usecase.data

import com.example.domain.repository.DetailDataRepository
import com.example.domain.response.ProjectDetailResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.common.Result

class DetailDataUseCase @Inject constructor(private val detailDataRepository: DetailDataRepository) {

    operator fun invoke(projectId:String): Flow<Result<ProjectDetailResponse>>{
        return detailDataRepository.getDetailData(projectId)
    }
}