package com.example.feature_home

import com.example.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(repository: Repository):ViewModelFactory{
        return ViewModelFactory(repository)
    }
}