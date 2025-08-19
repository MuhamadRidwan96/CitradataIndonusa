package com.example.domain.usecase.authentication

import android.util.Base64
import com.example.domain.model.UserProfile
import com.example.domain.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val userPref : UserPreferences) {
    operator fun invoke(): Flow<UserProfile?>{

        return userPref.getSession().map { session ->
            if (session.token.isBlank()) return@map null
            val payload = decodeJWTPayload(session.token)?: return@map null


            UserProfile(
                name = payload.optString("name", ""),
                email = payload.optString("email", ""),
                photo = payload.optString("photo", ""),
                idrole = payload.optString("idrole",""),
                role_name = payload.optString("role_name",""),
            )
        }
    }

    fun decodeJWTPayload(token: String): JSONObject? {
        return try {
            val parts = token.split(".")
            if (parts.size == 3) {
                val payload = String(
                    Base64.decode(parts[1], Base64.URL_SAFE)
                )
                JSONObject(payload)
            } else null

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}