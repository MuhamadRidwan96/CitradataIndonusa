package com.example.domain.model.subscription

import com.google.gson.annotations.SerializedName


data class Role(
    @SerializedName("idrole")
    val idRole: String,

    @SerializedName("name")
    val name: String
)

data class SubscriptionModel(

    val id: Int,
    val code: String,
    val name: String,
    val description: String,
    val price: Long,
    val currency: String,
    val duration: Int,
    val durationUnit: String,
    val role: Role
)



data class SubscriptionPlan(

    @SerializedName("id")
    val id: Int,

    @SerializedName("code")
    val code: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("price")
    val price: Double,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("duration")
    val duration: Int,

    @SerializedName("duration_unit")
    val durationUnit: String,

    @SerializedName("role")
    val role: Role
)
