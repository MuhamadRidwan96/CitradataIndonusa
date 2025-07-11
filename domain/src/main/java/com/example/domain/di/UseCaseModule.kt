package com.example.domain.di

import com.example.domain.preferences.UserPreferences
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.DataRepository
import com.example.domain.repository.LocationRepository
import com.example.domain.usecase.authentication.CheckLoginUseCase
import com.example.domain.usecase.authentication.GoogleSignInUseCase
import com.example.domain.usecase.authentication.LoginUseCase
import com.example.domain.usecase.authentication.RegisterUseCase
import com.example.domain.usecase.data.DataUseCase
import com.example.domain.usecase.location.CityUseCase
import com.example.domain.usecase.location.ProvinceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase{
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCheckLoginUseCase(userPreferences: UserPreferences,dispatcher: CoroutineDispatcher): CheckLoginUseCase {
        return CheckLoginUseCase(userPreferences,dispatcher)
    }

    @Provides
    @Singleton
    fun provideGoogleSignUseCase(
        repository: AuthRepository
    ): GoogleSignInUseCase = GoogleSignInUseCase(repository)

    @Provides
    @Singleton
    fun provideProvinceUseCase(
        repository: LocationRepository
    ): ProvinceUseCase {
        return ProvinceUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCityUseCase(
        repository: LocationRepository
    ): CityUseCase {
        return CityUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesDataUseCase(
        repository: DataRepository,
        dispatcher: CoroutineDispatcher
    ): DataUseCase{
        return DataUseCase(repository, dispatcher)
    }

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.IO
}