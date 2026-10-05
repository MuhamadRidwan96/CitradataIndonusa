package com.example.domain.response.subscription

import com.example.domain.model.subscription.FeatureStatus

data class SubscriptionFeatureResponse(
    val success: Boolean,
    val status: Int,
    val message: String,
    val data: FeatureStatus?
)
