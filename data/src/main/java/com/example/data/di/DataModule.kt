package com.example.data.di

import com.example.data.remote.google.GoogleAuthManager
import com.example.data.remote.api.ApiHelper
import com.example.data.remote.api.ApiHelperImpl
import com.example.data.remote.api.ApiService
import com.example.data.repositoryImpl.AuthenticationRepositoryImpl
import com.example.data.repositoryImpl.LocationRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApiHelperImpl(apiService: ApiService): ApiHelper {
        return ApiHelperImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRepository(
        apiHelper: ApiHelper,
        googleAuthManager: GoogleAuthManager,
    ): AuthRepository {
        return AuthenticationRepositoryImpl(apiHelper, googleAuthManager)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        apiHelper: ApiHelper
    ): LocationRepository{
        return LocationRepositoryImpl(apiHelper)
    }


}