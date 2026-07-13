package com.example.data.di

import android.app.Application
import androidx.room.Room
import com.example.data.local.dao.AppDatabase
import com.example.data.local.dao.FavoriteDAO
import com.example.data.local.dao.NotificationDao
import com.example.data.local.dao.ProfileDAO
import com.example.data.remote.api.ApiHelper
import com.example.data.remote.api.ApiHelperImpl
import com.example.data.remote.api.ApiService
import com.example.data.remote.google.GoogleAuthManager
import com.example.data.repositoryImpl.AuthenticationRepositoryImpl
import com.example.data.repositoryImpl.DataRepositoryImpl
import com.example.data.repositoryImpl.DetailDataRepositoryImpl
import com.example.data.repositoryImpl.FavoriteRepositoryImpl
import com.example.data.repositoryImpl.FilterDataRepositoryImpl
import com.example.data.repositoryImpl.LocationRepositoryImpl
import com.example.data.repositoryImpl.NotificationRepositoryImpl
import com.example.data.repositoryImpl.ProfileRepositoryImpl
import com.example.data.repositoryImpl.SaveTokenRepositoryImpl
import com.example.data.repositoryImpl.StatisticRepositoryImpl
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
    ): LocationRepository {
        return LocationRepositoryImpl(apiHelper)
    }

    @Provides
    @Singleton
    fun provideFilterDataRepository(
        apiHelper: ApiHelper
    ): FilterDataRepository {
        return FilterDataRepositoryImpl(apiHelper)
    }

    @Provides
    @Singleton
    fun provideDataRepository(apiHelper: ApiHelper): DataRepository {
        return DataRepositoryImpl(apiHelper)
    }

    @Singleton
    @Provides
    fun providesDetailRepository(apiHelper: ApiHelper): DetailDataRepository {
        return DetailDataRepositoryImpl(apiHelper)
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(dao: FavoriteDAO): FavoriteRepository {
        return FavoriteRepositoryImpl(dao)
    }


    @Singleton
    @Provides
    fun provideSaveTokenRepository(apiHelper: ApiHelper): SaveTokenRepository {
        return SaveTokenRepositoryImpl(apiHelper)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "my_database"
        ).fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun provideFavoriteDao(db: AppDatabase): FavoriteDAO {
        return db.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideNotificationDao(db: AppDatabase): NotificationDao {
        return db.notificationDao()
    }

    @Provides
    @Singleton
    fun provideProfileDao(db: AppDatabase) : ProfileDAO {
        return db.profileDao()
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(dao: NotificationDao): NotificationRepository {
        return NotificationRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideStatisticRepository(apiHelper: ApiHelper) : StatisticRepository{
        return StatisticRepositoryImpl(apiHelper)
    }

    @Provides
    @Singleton
    fun provideProfileRepository( apiHelper: ApiHelper,dao: ProfileDAO): ProfileRepository {
        return ProfileRepositoryImpl(apiHelper,dao)
    }

}