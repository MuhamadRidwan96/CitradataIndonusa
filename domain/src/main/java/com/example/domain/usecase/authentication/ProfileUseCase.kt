package com.example.domain.usecase.authentication

import com.example.domain.model.UserProfile
import com.example.domain.preferences.UserPreferences
import com.example.domain.utils.decodeJWTPayload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val userPref: UserPreferences) {
    operator fun invoke(): Flow<UserProfile?> {

        return userPref.getSession().map { session ->
            if (session.token.isBlank()) return@map null
            val payload = decodeJWTPayload(session.token) ?: return@map null

            val fullName = payload.optString("name", "")
            val firstName = fullName.split(" ").firstOrNull() ?: ""

            UserProfile(
                name = "$firstName!",
                email = payload.optString("email", ""),
                photo = payload.optString("photo", ""),
                idrole = payload.optString("idrole", ""),
                roleName = payload.optString("role_name", ""),
                iduser = payload.optString("iduser", "")
            )
        }
    }
}