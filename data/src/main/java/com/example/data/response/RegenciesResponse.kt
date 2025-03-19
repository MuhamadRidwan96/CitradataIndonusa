package com.example.data.response

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
    @SerializedName("idCity")
    val idCity:String,
    @SerializedName("idProvince")
    val idProvince:String?,
    @SerializedName("cityName")
    val cityName:String
)
