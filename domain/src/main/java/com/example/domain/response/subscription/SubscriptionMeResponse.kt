package com.example.domain.response.subscription

import com.example.domain.model.subscription.MySubscriptionData

data class SubscriptionMeResponse(
    val success: Boolean,
    val status: Int,
    val message: String,
    val data: MySubscriptionData?
)