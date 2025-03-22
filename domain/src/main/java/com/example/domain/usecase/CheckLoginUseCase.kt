package com.example.domain.usecase

import com.example.domain.preferences.UserPreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(private val userPreferences: UserPreferences) {

    suspend operator fun invoke(): Boolean {
        return userPreferences.getSession().map {
            it.isLogin
        }.firstOrNull() ?: false
    }
}