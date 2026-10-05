package com.example.domain.model.subscription

import com.google.gson.annotations.SerializedName

data class CreatedSubscriptionPlan(

    @SerializedName("id")
    val id: Int,

    @SerializedName("code")
    val code: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("duration")
    val duration: Int,

    @SerializedName("duration_unit")
    val durationUnit: String
)