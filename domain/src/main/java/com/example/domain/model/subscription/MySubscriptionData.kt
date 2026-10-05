package com.example.domain.model.subscription

import com.google.gson.annotations.SerializedName

data class MySubscriptionData(
    @SerializedName("subscription")
    val subscription: CurrentSubscription,

    @SerializedName("entitlements")
    val entitlements: SubscriptionEntitlements
)
