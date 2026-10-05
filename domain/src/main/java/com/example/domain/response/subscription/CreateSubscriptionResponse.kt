package com.example.domain.response.subscription

import com.example.domain.model.subscription.CreatedSubscription

data class CreateSubscriptionResponse(
    val success: Boolean,
    val status: Int,
    val message: String,
    val data: CreatedSubscription?
)
