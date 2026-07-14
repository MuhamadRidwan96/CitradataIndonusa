package com.example.domain.model

data class NotificationModel(
val id: Int,
val title: String,
val body: String,
val idProject: String?,
val isRead: Boolean,
val timestamp: Long

)
