package com.example.domain.model.subscription

import com.google.gson.annotations.SerializedName

data class SubscriptionPermission(

    @SerializedName("id")
    val id: Int,

    @SerializedName("idsubscription_plan")
    val subscriptionPlanId: Int,

    @SerializedName("permission_code")
    val permissionCode: String,

    @SerializedName("access")
    val access: String
)
