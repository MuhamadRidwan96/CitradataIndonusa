package com.example.data.local

import androidx.compose.ui.graphics.Color
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.data.local.entity.NotificationEntity
import com.example.data.utils.randomComposeColor
import com.example.domain.model.DonutData
import com.example.domain.model.FavoriteProject
import com.example.domain.model.NotificationModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

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

    fun mapToDonut(
        data: Map<String, Int>,
        colorMap: Map<String, Color>
    ): ImmutableList<DonutData> {
        return data.map { (label, value) ->
            DonutData(
                label = label,
                value = value.toFloat(),
                color = colorMap[label] ?: randomComposeColor()
            )
        }.toImmutableList()
    }