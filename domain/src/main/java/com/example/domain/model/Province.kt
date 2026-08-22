package com.example.domain.model

data class Province(
    val idProvince: String = "",
    val province: String = ""
)

data class StatisticProvince(
    val province : String,
    val total : Int
)