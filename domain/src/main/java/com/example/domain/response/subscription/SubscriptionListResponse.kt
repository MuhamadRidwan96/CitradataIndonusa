package com.example.domain.response.subscription

import com.example.domain.model.subscription.SubscriptionPlan

data class SubscriptionListResponse(
    val success: Boolean,
    val status: Int,
    val message: String,
    val data: List<SubscriptionPlan>?
)