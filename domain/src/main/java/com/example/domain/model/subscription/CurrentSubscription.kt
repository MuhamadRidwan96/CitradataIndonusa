package com.example.domain.model.subscription

import com.google.gson.annotations.SerializedName

data class CurrentSubscription(

    @SerializedName("idsubscription")
    val subscriptionId: Long,

    @SerializedName("iduser")
    val userId: String,

    @SerializedName("idsubscription_plan")
    val subscriptionPlanId: Int,

    @SerializedName("status")
    val status: String,

    @SerializedName("start_date")
    val startDate: String?,

    @SerializedName("end_date")
    val endDate: String?,

    @SerializedName("cancelled_at")
    val cancelledAt: String?,

    @SerializedName("created")
    val created: String,

    @SerializedName("createdby")
    val createdBy: String?,

    @SerializedName("updated")
    val updated: String?,

    @SerializedName("updatedby")
    val updatedBy: String?,

    @SerializedName("plan_code")
    val planCode: String,

    @SerializedName("plan_name")
    val planName: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("duration")
    val duration: String,

    @SerializedName("duration_unit")
    val durationUnit: String,

    @SerializedName("idrole")
    val roleId: String,

    @SerializedName("role_name")
    val roleName: String
)