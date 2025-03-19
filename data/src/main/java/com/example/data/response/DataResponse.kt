package com.example.data.response

import com.google.gson.annotations.SerializedName


data class DataResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<RecordData>,
    @SerializedName("Page") val page: Int,
    @SerializedName("Limit") val limit: Int,
    @SerializedName("TotalRecord") val totalRecord: Int
)

data class RecordData(
    @SerializedName("checkbox") val checkbox: String,
    @SerializedName("no") val no: Int,
    @SerializedName("lastUpdate") val lastUpdate: String,
    @SerializedName("idRecord") val idRecord: String,
    @SerializedName("idProject") val idProject:String,
    @SerializedName("project") val project: String,
    @SerializedName("statProject") val statProject: String,
    @SerializedName("category") val category: String,
    @SerializedName("status") val status: String,
    @SerializedName("location") val location: String,
    @SerializedName("province") val province: String
)