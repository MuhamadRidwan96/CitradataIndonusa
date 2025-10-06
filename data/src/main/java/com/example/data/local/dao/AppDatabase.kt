package com.example.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.data.local.entity.NotificationEntity

@Database(
    entities = [FavoriteProjectEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDAO
}

@Database(
    entities = [NotificationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase1: RoomDatabase(){
    abstract fun notificationDao(): NotificationDao
}

