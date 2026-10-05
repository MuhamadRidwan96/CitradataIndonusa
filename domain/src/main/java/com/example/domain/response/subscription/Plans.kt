package com.example.domain.response.subscription

import com.example.domain.model.subscription.SubscriptionModel
import com.google.gson.annotations.SerializedName

data class Plans(
    @SerializedName("success")
    val success : String,
    @SerializedName("status")
    val status : Int,
    @SerializedName("message")
    val message : String,
    val data : List<SubscriptionModel>
)