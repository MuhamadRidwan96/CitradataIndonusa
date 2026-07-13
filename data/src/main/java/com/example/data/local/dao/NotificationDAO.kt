package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: NotificationEntity)

    @Query("SELECT * FROM notification ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Query("SELECT COUNT(*) FROM notification WHERE isRead = 0")
    fun getUnreadCount(): Int

    @Query("UPDATE notification SET isRead = 1 WHERE id = :id")
    suspend fun markAllAsRead(id:Int)

    @Query("DELETE FROM notification WHERE id = :id")
    suspend fun deleteNotification(id:Int)

}