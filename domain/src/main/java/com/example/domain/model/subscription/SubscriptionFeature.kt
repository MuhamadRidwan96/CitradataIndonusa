package com.example.domain.model.subscription

import com.google.gson.annotations.SerializedName

data class SubscriptionFeature(

    @SerializedName("idsubscription_plan_feature")
    val id: Long,

    @SerializedName("idsubscription_plan")
    val subscriptionPlanId: Int,

    @SerializedName("feature_code")
    val featureCode: String,

    @SerializedName("is_active")
    val isActive: String,

    @SerializedName("created")
    val created: String?,

    @SerializedName("createdby")
    val createdBy: String?
)
