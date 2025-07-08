package com.example.domain.repository

import com.example.domain.model.CityModel
import com.example.domain.model.ProvinceModel
import com.example.domain.response.ProvinceResponse
import com.example.domain.response.RegenciesResponse
import kotlinx.coroutines.flow.Flow
import com.example.common.Result

interface LocationRepository {
    fun province(provinceModel: ProvinceModel?): Flow<Result<ProvinceResponse>>
    fun city(cityModel: CityModel): Flow<Result<RegenciesResponse>>
}