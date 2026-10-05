package com.example.domain.di

import com.example.domain.preferences.UserPreferences
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.DataRepository
import com.example.domain.repository.DetailDataRepository
import com.example.domain.repository.FavoriteRepository
import com.example.domain.repository.FilterDataRepository
import com.example.domain.repository.LocationRepository
import com.example.domain.repository.NotificationRepository
import com.example.domain.repository.ProfileRepository
import com.example.domain.repository.SaveTokenRepository
import com.example.domain.repository.StatisticRepository
import com.example.domain.usecase.authentication.CheckLoginUseCase
import com.example.domain.usecase.authentication.GoogleSignInUseCase
import com.example.domain.usecase.authentication.LoginUseCase
import com.example.domain.usecase.authentication.ProfileUseCase
import com.example.domain.usecase.authentication.RegisterUseCase
import com.example.domain.usecase.authentication.SaveTokenUseCase
import com.example.domain.usecase.data.DataUseCase
import com.example.domain.usecase.data.DetailDataUseCase
import com.example.domain.usecase.data.FilteredUseCase
import com.example.domain.usecase.location.CityUseCase
import com.example.domain.usecase.location.ProvinceUseCase
import com.example.domain.usecase.notification.DeleteNotificationUseCase
import com.example.domain.usecase.notification.MarkNotificationReadUseCase
import com.example.domain.usecase.notification.ObserveNotificationUseCase
import com.example.domain.usecase.notification.ObserveUnreadCountUseCase
import com.example.domain.usecase.notification.SyncNotificationUseCase
import com.example.domain.usecase.profile.ClearProfileUseCase
import com.example.domain.usecase.profile.ObserverProfileUseCase
import com.example.domain.usecase.profile.RefreshProfileUseCase
import com.example.domain.usecase.room.DeleteFavoriteUseCase
import com.example.domain.usecase.room.FavoriteUseCase
import com.example.domain.usecase.room.GetAllFavoriteUseCase
import com.example.domain.usecase.room.InsertFavoriteUseCase
import com.example.domain.usecase.statistic.StatisticUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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
    fun provideRegisterUseCase(repository: AuthRepository,@IoDispatcher dispatcher: CoroutineDispatcher): RegisterUseCase {
        return RegisterUseCase(repository, dispatcher)
    }

    @Provides
    @Singleton
    fun provideCheckLoginUseCase(
        userPreferences: UserPreferences,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CheckLoginUseCase {
        return CheckLoginUseCase(userPreferences, dispatcher)
    }

    @Provides
    @Singleton
    fun provideGoogleSignUseCase(
        repository: AuthRepository
    ): GoogleSignInUseCase = GoogleSignInUseCase(repository)

    @Provides
    @Singleton
    fun provideProvinceUseCase(
        repository: LocationRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): ProvinceUseCase {
        return ProvinceUseCase(repository, dispatcher)
    }

    @Provides
    @Singleton
    fun provideCityUseCase(
        repository: LocationRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CityUseCase {
        return CityUseCase(repository, dispatcher)
    }

    @Provides
    @Singleton
    fun providesDataUseCase(
        repository: DataRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): DataUseCase {
        return DataUseCase(repository, dispatcher)
    }

    @Provides
    @Singleton
    fun providesDetailUseCase(repository: DetailDataRepository, @IoDispatcher dispatcher: CoroutineDispatcher): DetailDataUseCase {
        return DetailDataUseCase(repository, dispatcher)
    }

    @Provides
    @Singleton
    fun providesProfileUseCase(userPref: UserPreferences): ProfileUseCase {
        return ProfileUseCase(userPref)
    }

    @Provides
    @Singleton
    fun provideFilteredUseCase(
        repository: FilterDataRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): FilteredUseCase {
        return FilteredUseCase(repository, dispatcher)
    }

    @Provides
    @Singleton
    fun provideGetAllFavoriteUseCase(repository: FavoriteRepository): GetAllFavoriteUseCase {
        return GetAllFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertFavoriteUseCase(repository: FavoriteRepository): InsertFavoriteUseCase {
        return InsertFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteFavoriteUseCase(repository: FavoriteRepository): DeleteFavoriteUseCase {
        return DeleteFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideIsFavoriteUseCase(repository: FavoriteRepository): FavoriteUseCase {
        return FavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveTokenUseCase(repository: SaveTokenRepository): SaveTokenUseCase {
        return SaveTokenUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideResetNotificationCountUseCase(repository: NotificationRepository): MarkNotificationReadUseCase {
        return MarkNotificationReadUseCase(repository)
    }



    @Provides
    @Singleton
    fun provideGetAllNotificationUseCase(repository: NotificationRepository): ObserveNotificationUseCase {
        return ObserveNotificationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteUseCase(repository: NotificationRepository): DeleteNotificationUseCase {
        return DeleteNotificationUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideStatisticUseCase(
        repository: StatisticRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): StatisticUseCase {
        return StatisticUseCase(repository, dispatcher)
    }

    @Provides
    @Singleton
    fun providesApiProfileUseCase(
        repository: ProfileRepository,
    ): ObserverProfileUseCase {
        return ObserverProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRefreshProfileUseCase(
        repository: ProfileRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ):RefreshProfileUseCase{
        return RefreshProfileUseCase(repository,dispatcher)
    }

    @Provides
    @Singleton
    fun provideClearProfileUseCase(
    repository: ProfileRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher) : ClearProfileUseCase{
        return ClearProfileUseCase(repository,dispatcher)
    }

    @Provides
    @Singleton
    fun provideSyncNotificationUseCase(repository: NotificationRepository) : SyncNotificationUseCase{
        return SyncNotificationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUnreadNotificationUseCase(repository: NotificationRepository): ObserveUnreadCountUseCase{
        return ObserveUnreadCountUseCase(repository)
    }
}