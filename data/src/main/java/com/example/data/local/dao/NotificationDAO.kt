package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notification ORDER BY createdAt DESC")
    fun observeNotifications(): Flow<List<NotificationEntity>>

    @Query("SELECT COUNT(*) FROM notification WHERE isRead = 0")
    fun observeUnreadCount(): Flow<Int>

    @Upsert
    suspend fun upsertAll(list: List<NotificationEntity>)

    @Transaction
    suspend fun sync(list: List<NotificationEntity>) {

        if (list.isEmpty()){
            deleteAll()
            return
        }

        deleteNotIn(list.map{it.id})

        upsertAll(list)

    }

    @Query("DELETE FROM notification WHERE id NOT IN (:ids)")
    suspend fun deleteNotIn(ids: List<Int>)

    @Query("DELETE FROM notification")
    suspend fun deleteAll()

    @Query("UPDATE notification SET isRead = 1 WHERE id=:id")
    suspend fun markAsRead(id: Int)

    @Query("UPDATE notification SET isRead = 1")
    suspend fun markAllAsRead()

    @Query("DELETE FROM notification WHERE id = :id")
    suspend fun deleteNotification(id: Int)
}