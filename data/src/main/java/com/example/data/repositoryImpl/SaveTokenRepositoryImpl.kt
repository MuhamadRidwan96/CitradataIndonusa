package com.example.data.repositoryImpl

import com.example.data.network.api.ApiHelper
import com.example.domain.repository.SaveTokenRepository
import timber.log.Timber
import javax.inject.Inject

class SaveTokenRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper
) : SaveTokenRepository {

    override suspend fun saveToken(userId: String, token: String) {

        try {
            val response = apiHelper.saveToken(userId, token)

            if (!response.isSuccessful) {
                Timber.e(response.errorBody()?.toString())
                return
            }

            Timber.d("Save token success")

        } catch (e: Exception) {
            Timber.e(e)
        }

    }
}