package com.example.data.utils

import org.json.JSONObject

object ErrorHandle {
    fun parseErrorBody(errorBody: String): String? {
        return try {
            val jsonObject = JSONObject(errorBody)
            jsonObject.getString("message")
        } catch (e: Exception) {
            null
        }
    }
}