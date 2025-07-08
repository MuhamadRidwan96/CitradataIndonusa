package com.example.domain.usecase.location

import com.example.common.Result
import com.example.domain.model.ProvinceModel
import com.example.domain.repository.LocationRepository
import com.example.domain.response.ProvinceResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProvinceUseCase @Inject constructor(
    private val repository: LocationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(provinceModel: ProvinceModel): Flow<Result<ProvinceResponse>> {
        return repository.province(provinceModel)
            .flowOn(dispatcher)
    }
}

