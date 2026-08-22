package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class NotificationModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: String,
    val title: String,
    val body: String,
    @SerializedName("project_id")
    val projectId: String?,
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("is_read")
    val isRead: Boolean,
    @SerializedName("created_at")
    val createdAt: String
)