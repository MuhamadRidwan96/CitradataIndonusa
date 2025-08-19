package com.example.data.repositoryImpl

import com.example.data.remote.api.ApiHelper
import com.example.data.utils.toResult
import com.example.domain.model.CityModel
import com.example.domain.model.ProvinceModel
import com.example.domain.repository.LocationRepository
import com.example.domain.response.ProvinceResponse
import com.example.domain.response.RegenciesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.common.Result

class LocationRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper
) : LocationRepository {
    override fun province(provinceModel: ProvinceModel?): Flow<Result<ProvinceResponse>> = flow {

        val response = apiHelper.getProvince(provinceModel)
        emit(response.toResult())

    }
    override fun city(cityModel: CityModel): Flow<Result<RegenciesResponse>> = flow {

        val cityResponse = apiHelper.getCity(cityModel)
        emit(cityResponse.toResult())
    }
}
