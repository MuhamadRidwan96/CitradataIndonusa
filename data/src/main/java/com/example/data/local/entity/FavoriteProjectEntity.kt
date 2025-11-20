package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_projects")
data class FavoriteProjectEntity(
    @PrimaryKey val idProject:Int,
    val lastUpdate: String,
    val idRecord: String,
    val project: String,
    val statProject: String,
    val category: String,
    val status: String,
    val location: String,
    val province: String
)
