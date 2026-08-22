package com.example.data.utils

import com.example.domain.model.CityModel
import com.example.domain.model.Province
import com.example.domain.response.DataProvince
import com.example.domain.response.DataRegencies
import com.example.domain.response.ProvinceResponse
import com.example.domain.response.RegenciesResponse

fun DataProvince.toDomain(): Province {
    return Province(
        idProvince = idProvince,
        province = province
    )
}

fun DataRegencies.toDomain(): CityModel {
    return CityModel(
        idCity = idCity,
        idProvince = idProvince,
        cityName = cityName
    )
}

fun RegenciesResponse.toDomain(): List<CityModel> {
    return data.map { it.toDomain() }
}

fun ProvinceResponse.toDomain(): List<Province> {
    return data.map { it.toDomain() }
}
