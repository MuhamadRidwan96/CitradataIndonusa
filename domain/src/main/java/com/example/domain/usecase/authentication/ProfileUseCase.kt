package com.example.domain.usecase.authentication

import com.example.domain.model.UserProfile
import com.example.domain.preferences.UserPreferences
import com.example.domain.utils.decodeJWTPayload
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val userPref: UserPreferences
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<UserProfile?> {

        return userPref.getSession().mapLatest { session ->
            if (session.token.isBlank()) return@mapLatest null
            val payload = decodeJWTPayload(session.token) ?: return@mapLatest null

            val fullName = payload.optString("name", "")
            val firstName = fullName.split(" ").firstOrNull() ?: ""


            UserProfile(
                name = "$firstName!",
                fullName = payload.optString("full_name", ""),
                email = payload.optString("email", ""),
                photo = payload.optString("photo", ""),
                idrole = payload.optString("idrole", ""),
                roleName = payload.optString("role_name", ""),
                idUser = payload.optString("",""),
                idUserMaster = payload.optString("",""),
                idRole = payload.optString("",""),
                idProvince = payload.optString("",""),
                idCity = payload.optString("",""),
                username = payload.optString("",""),
                password = payload.optString("",""),
                address = payload.optString("",""),
                position = payload.optString("",""),
                company = payload.optString("",""),
                phone = payload.optString("",""),
                note = payload.optString("",""),
                userStatus = payload.optString("",""),
                packageMemberType = payload.optString("",""),
                subscriptionFee = payload.optString("",""),
                totalFee = payload.optString("",""),
                counted = payload.optString("",""),
                website = payload.optString("",""),
                startDate = payload.optString("",""),
                endDate = payload.optString("",""),
                userType = payload.optString("",""),
                created = payload.optString("",""),
                createdBy = payload.optString("",""),
                updated = payload.optString("",""),
                updatedBy = payload.optString("",""),
                status = payload.optString("",""),
            )
        }
    }
}