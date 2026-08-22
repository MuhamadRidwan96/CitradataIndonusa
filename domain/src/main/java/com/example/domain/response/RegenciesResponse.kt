package com.example.domain.response

import com.google.gson.annotations.SerializedName

data class RegenciesResponse(

    @SerializedName("success")
    val success : Boolean,
    @SerializedName("status")
    val status:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data:List<DataRegencies>
)

data class DataRegencies (
    @SerializedName("id_city")
    val idCity:String,
    @SerializedName("id_province")
    val idProvince:String ?,
    @SerializedName("city_name")
    val cityName:String
)

data class CityRequest(
    @SerializedName("id_city")
    val idCity:String ?,
    @SerializedName("id_province")
    val idProvince:String ?,
    @SerializedName("city_name")
    val cityName:String ?
)
