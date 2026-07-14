package com.example.data.repositoryImpl

import com.example.data.remote.api.ApiHelper
import com.example.domain.repository.SaveTokenRepository
import javax.inject.Inject

class SaveTokenRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper): SaveTokenRepository {
    override suspend fun saveToken(userId: String, token: String) {
       val response = apiHelper.saveToken(userId,token)

        if (!response.isSuccessful) {
            throw Exception("Failed to save FCM token")
        }
    }
}