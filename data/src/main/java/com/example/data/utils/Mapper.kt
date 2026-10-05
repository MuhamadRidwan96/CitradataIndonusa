package com.example.data.utils

import com.example.domain.model.location.CityModel
import com.example.domain.model.location.Province
import com.example.domain.response.location.DataProvince
import com.example.domain.response.location.DataRegencies
import com.example.domain.response.location.ProvinceResponse
import com.example.domain.response.location.RegenciesResponse

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
