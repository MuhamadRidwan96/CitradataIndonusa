package com.example.domain.usecase.location

import com.example.domain.repository.LocationRepository
import com.example.domain.response.RegenciesResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.common.Result
import com.example.domain.model.CityModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class CityUseCase @Inject constructor(
    private val repository: LocationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(
        idCity: String,
        idProvince: String?,
        cityName: String
    ): Flow<Result<RegenciesResponse>> =
        repository.city(CityModel(idCity, idProvince, cityName))
            .flowOn(dispatcher)
}