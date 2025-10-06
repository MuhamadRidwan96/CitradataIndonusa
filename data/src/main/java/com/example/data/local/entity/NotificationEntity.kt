package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notification")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val title :String,
    val body: String,
    val isRead : Boolean,
    val timestamp: Long = System.currentTimeMillis()
)