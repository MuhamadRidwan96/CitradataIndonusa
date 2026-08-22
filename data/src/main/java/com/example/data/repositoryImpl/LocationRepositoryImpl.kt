package com.example.data.repositoryImpl

import android.util.Log
import com.example.data.network.api.ApiHelper
import com.example.data.utils.toDomain
import com.example.data.utils.toResult
import com.example.domain.model.CityModel
import com.example.domain.model.Province
import com.example.domain.repository.LocationRepository
import com.example.domain.response.CityRequest
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper
) : LocationRepository {
    override suspend fun province(provinceModel: Province?): Result<List<Province>>{
        return apiHelper.getProvince(provinceModel).toResult()
            .map{ response ->
                response.toDomain()
            }

    }

    override suspend fun city(cityModel: CityModel): Result<List<CityModel>> {

        return apiHelper.getCity(

            CityRequest(
                idCity = cityModel.idCity,
                idProvince = cityModel.idProvince,
                cityName = cityModel.cityName
            )
        ).toResult()
            .map{response ->
                response.toDomain()
            }
    }
}
