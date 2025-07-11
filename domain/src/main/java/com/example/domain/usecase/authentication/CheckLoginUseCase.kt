package com.example.domain.usecase.authentication

import com.example.domain.preferences.UserPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(
    private val userPreferences: UserPreferences,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Boolean {
        return withContext(dispatcher) {
            userPreferences.getSession()
                .map { it.isLogin }
                .firstOrNull() == true
        }
    }
}