package com.example.domain.usecase.location

import com.example.common.Result
import com.example.domain.di.IoDispatcher
import com.example.domain.model.CityModel
import com.example.domain.repository.LocationRepository
import com.example.domain.response.RegenciesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CityUseCase @Inject constructor(
    private val repository: LocationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(
        idCity: String,
        idProvince: String?,
        cityName: String
    ): Flow<Result<RegenciesResponse>> =
        repository.city(CityModel(idCity, idProvince, cityName))
            .flowOn(dispatcher)
}