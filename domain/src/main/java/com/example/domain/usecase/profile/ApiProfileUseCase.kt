package com.example.domain.usecase.profile

import com.example.common.Result
import com.example.domain.di.IoDispatcher
import com.example.domain.repository.ProfileRepository
import com.example.domain.response.ProfileResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<Result<ProfileResponse>>{
        return withContext(dispatcher){ repository.getUser()  }
    }
}