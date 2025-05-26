package com.example.domain.usecase.location

import com.example.domain.model.ProvinceModel
import com.example.domain.repository.LocationRepository
import com.example.domain.response.ProvinceResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class ProvinceUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    operator fun invoke(provinceModel: ProvinceModel): Flow<Result<ProvinceResponse>> =
        repository.province(provinceModel)
            .catch { e -> emit(Result.failure(e)) }
}