package com.example.data.di

import com.example.data.GoogleAuthManager
import com.example.data.remote.api.ApiHelper
import com.example.data.remote.api.ApiHelperImpl
import com.example.data.remote.api.ApiService
import com.example.data.remote.firebase.FireStoreService
import com.example.data.repositoryImpl.RepositoryImpl
import com.example.domain.repository.AuthRepository
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
        firestoreService: FireStoreService
    ): AuthRepository {
        return RepositoryImpl(apiHelper, googleAuthManager, firestoreService)
    }
}