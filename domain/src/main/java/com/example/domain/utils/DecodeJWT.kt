package com.example.domain.utils

import android.util.Base64
import org.json.JSONObject

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