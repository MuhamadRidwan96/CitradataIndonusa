package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notification")
data class NotificationEntity(
    @PrimaryKey val id:Int,
    val userId : String,
    val title :String,
    val body: String,
    val projectId: String?,
    val projectName: String,
    val isRead : Boolean,
    val createdAt: String

)