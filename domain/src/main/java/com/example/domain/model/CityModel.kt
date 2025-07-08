package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class CityModel(
    @SerializedName("id_city")
    val idCity: String = "",
    @SerializedName("id_province")
    val idProvince: String? = null,
    @SerializedName("city_name")
    val cityName: String =""
)
