package com.example.domain.model.subscription

import com.google.gson.annotations.SerializedName

data class CreatedSubscription(
    @SerializedName("idsubscription")
    val subscriptionId: Long,

    @SerializedName("status")
    val status: String,

    @SerializedName("plan")
    val plan: CreatedSubscriptionPlan
)
