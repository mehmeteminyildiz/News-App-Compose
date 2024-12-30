package com.mey.newsapp.di

import android.app.Application
import com.mey.newsapp.data.manager.LocalUserManagerIml
import com.mey.newsapp.domain.manager.LocalUserManager
import com.mey.newsapp.domain.usecases.AppEntryUseCases
import com.mey.newsapp.domain.usecases.ReadAppEntry
import com.mey.newsapp.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Local User Manager
    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerIml(application)


    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )


}