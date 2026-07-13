package com.example.features.presentation.profile.screen.state

import com.example.domain.model.UserProfile

fun UserProfile.toProfileState() = ProfileState(
    statusInfo = ProfileState.StatusInfo(
        isLoading = false
    ),
    basicInfo = ProfileState.BasicInfo(
        name = name,
        fullName = name,
        photo = photo,
        username = username
    ),
    contactInfo = ProfileState.ContactInfo(
        email = email,
        phone = phone,
        address = address,
        website = website
    ),
    professionalInfo = ProfileState.ProfessionalInfo(
        position = position,
        company = company,
        note = note
    ),
    accountInfo = ProfileState.AccountInfo(
        idUser = idUser,
        idUserMaster = idUserMaster,
        idRole = idRole,
        roleName = roleName,
        userStatus = userStatus,
        userType = userType
    ),
    subscriptionInfo = ProfileState.SubscriptionInfo(
        packageMemberType = packageMemberType,
        subscriptionFee = subscriptionFee,
        totalFee = totalFee,
        counted = counted,
        startDate = startDate,
        endDate = endDate
    ),
    metadata = ProfileState.Metadata(
        idProvince = idProvince,
        idCity = idCity,
        created = created,
        createdBy = createdBy,
        updated = updated,
        updatedBy = updatedBy,
        status = status
    ),
)