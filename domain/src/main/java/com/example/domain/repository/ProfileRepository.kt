package com.example.domain.repository

import com.example.common.Result
import com.example.domain.response.ProfileResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUser(): Flow<Result<ProfileResponse>>
}