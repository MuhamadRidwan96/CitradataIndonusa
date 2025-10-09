package com.example.data.local

import com.example.data.local.entity.FavoriteProjectEntity
import com.example.data.local.entity.NotificationEntity
import com.example.domain.model.FavoriteProject
import com.example.domain.model.NotificationModel

fun FavoriteProjectEntity.toDomain() = FavoriteProject(
    idProject,
    lastUpdate,
    idRecord,
    project,
    statProject,
    category,
    status,
    location,
    province
)

fun FavoriteProject.toEntity() = FavoriteProjectEntity(
    idProject,
    lastUpdate,
    idRecord,
    project,
    statProject,
    category,
    status,
    location,
    province
)

fun NotificationEntity.toDomain() = NotificationModel(
    id,
    title,
    body,
    isRead,
    timestamp
)

fun NotificationModel.toEntity() = NotificationEntity(
    id ,
    title,
    body,
    isRead,
    timestamp
)