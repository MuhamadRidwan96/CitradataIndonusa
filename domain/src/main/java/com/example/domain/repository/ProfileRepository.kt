package com.example.domain.repository


import com.example.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun observeProfile(): Flow<UserProfile?>

    suspend fun refreshProfile()

    suspend fun clearProfile()
}