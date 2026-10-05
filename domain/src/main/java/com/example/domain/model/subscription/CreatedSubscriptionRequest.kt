package com.example.domain.model.subscription

import com.google.gson.annotations.SerializedName

data class CreatedSubscriptionRequest(
    @SerializedName("plan_id")
    val planId: Int
)
