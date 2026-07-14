package com.example.data.local

import androidx.compose.ui.graphics.Color
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.data.local.entity.NotificationEntity
import com.example.data.local.entity.ProfileEntity
import com.example.data.utils.randomComposeColor
import com.example.domain.model.DonutData
import com.example.domain.model.FavoriteProject
import com.example.domain.model.NotificationModel
import com.example.domain.model.UserProfile
import com.example.domain.response.UserData
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
    idProject,
    isRead,
    timestamp
)

fun NotificationModel.toEntity() = NotificationEntity(
    id ,
    title,
    body,
    idProject,
    isRead,
    timestamp
)

fun ProfileEntity.toDomain() = UserProfile(
    idUser = idUser,
    name = name,
    fullName = fullName,
    email = email,
    photo = photo,
    roleName = roleName,
    idUserMaster = idUserMaster,
    idRole = idRole,
    idProvince = idProvince,
    idCity = idCity,
    username = username,
    password = password,
    address = address,
    position = position,
    company = company,
    phone = phone,
    note = note,
    userStatus = userStatus,
    packageMemberType = packageMemberType,
    subscriptionFee = subscriptionFee,
    totalFee = totalFee,
    counted = counted,
    website = website,
    startDate = startDate,
    endDate = endDate,
    userType = userType,
    created = created,
    createdBy = createdBy,
    updated = updated,
    updatedBy = updatedBy,
    status = status
)

fun UserData.toEntity() = ProfileEntity(
    idUser = idUser,
    name = name,
    email = email,
    photo = photo,
    idUserMaster = idUserMaster,
    idRole = idRole,
    idProvince = idProvince,
    idCity = idCity,
    username = username,
    password = password,
    address = address,
    position = position,
    company = company,
    phone = phone,
    note = note,
    userStatus = userStatus,
    packageMemberType = packageMemberType,
    subscriptionFee = subscriptionFee,
    totalFee = totalFee,
    counted = counted,
    website = website,
    startDate = startDate,
    endDate = endDate,
    userType = userType,
    created = created,
    createdBy = createdBy,
    updated = updated,
    updatedBy = updatedBy,
    status = status,
    fullName = name,
    roleName = name
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