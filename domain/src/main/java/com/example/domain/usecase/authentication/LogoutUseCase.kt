package com.example.domain.usecase.authentication

import com.example.domain.preferences.UserPreferences
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val userPreferences: UserPreferences) {
    suspend operator fun invoke() {
        userPreferences.logout()
    }
}