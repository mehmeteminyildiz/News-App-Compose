package com.mey.newsapp.di

import android.app.Application
import com.mey.newsapp.data.manager.LocalUserManagerIml
import com.mey.newsapp.data.remote.NewsApi
import com.mey.newsapp.data.repository.NewsRepositoryImpl
import com.mey.newsapp.domain.manager.LocalUserManager
import com.mey.newsapp.domain.repository.NewsRepository
import com.mey.newsapp.domain.usecases.app_entry.AppEntryUseCases
import com.mey.newsapp.domain.usecases.app_entry.ReadAppEntry
import com.mey.newsapp.domain.usecases.app_entry.SaveAppEntry
import com.mey.newsapp.domain.usecases.news.GetNews
import com.mey.newsapp.domain.usecases.news.NewsUseCases
import com.mey.newsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository = NewsRepositoryImpl(newsApi)


    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository)
        )
    }
}