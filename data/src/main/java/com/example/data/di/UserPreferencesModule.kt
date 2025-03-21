package com.example.data.di

import com.example.data.preferencesImpl.UserPreferencesImpl
import com.example.domain.preferences.UserPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract object AuthModule {
    
    @Binds
    @Singleton
    abstract fun bindUserPreferences(
        userPreferencesImpl: UserPreferencesImpl
    ): UserPreferences
}