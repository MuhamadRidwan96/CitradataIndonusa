package com.example.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.data.local.entity.NotificationEntity

@Database(
    entities = [FavoriteProjectEntity::class, NotificationEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDAO
    abstract fun notificationDao(): NotificationDao
}


