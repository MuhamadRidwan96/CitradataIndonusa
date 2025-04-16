package com.example.domain.di

import com.example.domain.preferences.UserPreferences
import com.example.domain.repository.AuthRepository
import com.example.domain.usecase.authentication.CheckLoginUseCase
import com.example.domain.usecase.authentication.GoogleSignInUseCase
import com.example.domain.usecase.authentication.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(
        repository: AuthRepository,
        userPreferences: UserPreferences
    ): LoginUseCase {
        return LoginUseCase(repository, userPreferences)
    }

    @Provides
    @Singleton
    fun provideCheckLoginUseCase(userPreferences: UserPreferences): CheckLoginUseCase {
        return CheckLoginUseCase(userPreferences)
    }

    @Provides
    @Singleton
    fun provideGoogleSignUseCase(
        repository: AuthRepository
    ): GoogleSignInUseCase = GoogleSignInUseCase(repository)
}