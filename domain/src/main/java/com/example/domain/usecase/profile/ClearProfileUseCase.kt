package com.example.domain.usecase.profile

import com.example.domain.di.IoDispatcher
import com.example.domain.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ClearProfileUseCase(
    private val repository: ProfileRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() = withContext(dispatcher){
        repository.clearProfile()
    }
}