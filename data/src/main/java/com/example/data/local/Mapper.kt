package com.example.data.local

import com.example.data.local.entity.FavoriteProjectEntity
import com.example.domain.model.FavoriteProject

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