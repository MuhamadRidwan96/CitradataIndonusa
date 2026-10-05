package com.example.domain.model.subscription

import com.google.gson.annotations.SerializedName

data class EntitlementPlan(

    @SerializedName("idsubscription_plan")
    val subscriptionPlanId: Int,

    @SerializedName("idrole")
    val roleId: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("price")
    val price: String,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("duration")
    val duration: String,

    @SerializedName("duration_unit")
    val durationUnit: String,

    @SerializedName("is_trial")
    val isTrial: String,

    @SerializedName("is_active")
    val isActive: String,

    @SerializedName("role_name")
    val roleName: String
)
