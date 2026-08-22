package com.example.domain.repository

import com.example.domain.model.CityModel
import com.example.domain.model.Province

interface LocationRepository {
    suspend fun province(provinceModel: Province?): Result<List<Province>>
    suspend fun city(cityModel: CityModel): Result<List<CityModel>>
}