package com.example.domain.model.subscription

import com.google.gson.annotations.SerializedName


data class FeatureStatus(

    @SerializedName("feature_code")
    val featureCode: String,

    @SerializedName("allowed")
    val allowed: Boolean
)

