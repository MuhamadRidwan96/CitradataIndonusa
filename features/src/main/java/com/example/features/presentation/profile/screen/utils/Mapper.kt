package com.example.features.presentation.profile.screen.utils

import com.example.domain.model.UserProfile
import com.example.features.presentation.profile.screen.state.ProfileUiState

fun UserProfile.toProfileState() = ProfileUiState(
    statusInfo = ProfileUiState.StatusInfo(
        isLoading = false
    ),
    basicInfo = ProfileUiState.BasicInfo(
        name = name,
        fullName = name,
        photo = photo,
        username = username
    ),
    contactInfo = ProfileUiState.ContactInfo(
        email = email,
        phone = phone,
        address = address,
        website = website
    ),
    professionalInfo = ProfileUiState.ProfessionalInfo(
        position = position,
        company = company,
        note = note
    ),
    accountInfo = ProfileUiState.AccountInfo(
        idUser = idUser,
        idUserMaster = idUserMaster,
        idRole = idRole,
        roleName = roleName,
        userStatus = userStatus,
        userType = userType
    ),
    subscriptionInfo = ProfileUiState.SubscriptionInfo(
        packageMemberType = packageMemberType,
        subscriptionFee = subscriptionFee,
        totalFee = totalFee,
        counted = counted,
        startDate = startDate,
        endDate = endDate
    ),
    metadata = ProfileUiState.Metadata(
        idProvince = idProvince,
        idCity = idCity,
        created = created,
        createdBy = createdBy,
        updated = updated,
        updatedBy = updatedBy,
        status = status
    )
)