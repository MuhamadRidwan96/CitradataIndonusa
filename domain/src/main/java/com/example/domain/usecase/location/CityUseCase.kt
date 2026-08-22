package com.example.domain.usecase.location

import com.example.domain.di.IoDispatcher
import com.example.domain.model.CityModel
import com.example.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityUseCase @Inject constructor(
    private val repository: LocationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        idCity: String,
        idProvince: String?,
        cityName: String
    ): Result<List<CityModel>> {

        return withContext(dispatcher){
            repository.city(CityModel(idCity, idProvince, cityName))
        }
    }
}