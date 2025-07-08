package com.example.domain.response

import com.google.gson.annotations.SerializedName

data class ProvinceResponse(

    @SerializedName("success")
    val success : Boolean,
    @SerializedName("status")
    val status:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data:List<DataProvince>

)

data class DataProvince(
    @SerializedName("idProvince")
    val idProvince:String,
    @SerializedName("province")
    val province:String

)