package com.example.domain.response

import com.example.domain.model.NotificationModel
import com.google.gson.annotations.SerializedName

    data class NotificationResponse(
        @SerializedName("success")
        val success: Boolean,
        @SerializedName("data")
        val data: List<NotificationModel>
    )