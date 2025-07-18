package com.example.data.utils

import org.json.JSONObject

object ErrorHandle {
    fun parseErrorBody(errorBody: String): String? {
        return try {
            val jsonObject = JSONObject(errorBody)
            if (jsonObject.has("message")) {
                jsonObject.getString("message")
            } else {
                null
            }
        } catch (_: Exception) {
            null
        }
    }
}