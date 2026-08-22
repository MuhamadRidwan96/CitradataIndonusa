package com.example.data.repositoryImpl

import com.example.data.local.dao.ProfileDAO
import com.example.data.local.toDomain
import com.example.data.local.toEntity
import com.example.data.network.api.ApiHelper
import com.example.domain.model.UserProfile
import com.example.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dao: ProfileDAO
) : ProfileRepository {

    override fun observeProfile(): Flow<UserProfile?> {
        return dao.observeProfile()
            .map {it?.toDomain()}
    }


    override suspend fun refreshProfile() {
        val response = apiHelper.getUser()
        if (response.isSuccessful){
            response.body()?.let { profileResponse ->
                dao.insert(profileResponse.data.toEntity())
            }
        }
        if (!response.isSuccessful){
            throw Exception(
                response.message().ifEmpty {
                    "Failed load profile"
                }
            )
        }
    }

    override suspend fun clearProfile() {
        dao.clear()
    }
}