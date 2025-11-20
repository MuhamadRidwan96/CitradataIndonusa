package com.example.domain.response

import com.google.gson.annotations.SerializedName

data class StatisticsResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: StatisticsData,
    @SerializedName("source_data_count")
    val sourceDataCount: Int
)

data class StatisticsData(
    @SerializedName("total_projects")
    val totalProjects: Int,
    @SerializedName("by_category")
    val byCategory: Map<String, Int>,
    @SerializedName("by_status")
    val byStatus: Map<String, Int>,
    @SerializedName("by_province")
    val byProvince: Map<String, Int>
)