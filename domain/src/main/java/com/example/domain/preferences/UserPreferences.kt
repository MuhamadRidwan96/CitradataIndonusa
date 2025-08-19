package com.example.domain.preferences

import com.example.domain.model.UserModel
import kotlinx.coroutines.flow.Flow


interface UserPreferences {
    suspend fun saveSession(user: UserModel)
    fun getSession(): Flow<UserModel>
    suspend fun logout()
    suspend fun saveToken(token: String)
}