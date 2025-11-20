package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.FavoriteProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorites(project: FavoriteProjectEntity)

    @Query("DELETE FROM favorite_projects WHERE idProject = :id")
    suspend fun removeFromFavorites(id: Int)

    @Query("SELECT * FROM favorite_projects")
    fun getAllFavorites(): Flow<List<FavoriteProjectEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_projects WHERE idProject = :id)")
    suspend fun isFavorite(id: Int): Boolean
}