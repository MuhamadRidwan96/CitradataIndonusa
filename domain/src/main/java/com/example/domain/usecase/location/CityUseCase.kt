package com.example.domain.usecase.location

import com.example.domain.model.CityModel
import com.example.domain.repository.LocationRepository
import com.example.domain.response.RegenciesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class CityUseCase @Inject constructor(
   private val repository: LocationRepository
) {
    operator fun invoke(idCity:String, idProvince: String, cityName:String) : Flow<Result<RegenciesResponse>> =
        repository.city(CityModel(idCity,idProvince,cityName))
            .catch { e-> emit(Result.failure(e)) }

}