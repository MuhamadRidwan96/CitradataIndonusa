package com.example.domain.response.subscription

data class SubscriptionCancelResponse(
    val success: Boolean,
    val status: Int,
    val message: String,
    val data: Any?
)
