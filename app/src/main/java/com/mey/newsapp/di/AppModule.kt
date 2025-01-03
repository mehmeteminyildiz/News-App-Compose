package com.mey.newsapp.di

import android.app.Application
import androidx.room.Room
import com.mey.newsapp.data.local.NewsDao
import com.mey.newsapp.data.local.NewsDatabase
import com.mey.newsapp.data.local.NewsTypeConverter
import com.mey.newsapp.data.manager.LocalUserManagerIml
import com.mey.newsapp.data.remote.NewsApi
import com.mey.newsapp.data.repository.NewsRepositoryImpl
import com.mey.newsapp.domain.manager.LocalUserManager
import com.mey.newsapp.domain.repository.NewsRepository
import com.mey.newsapp.domain.usecases.app_entry.AppEntryUseCases
import com.mey.newsapp.domain.usecases.app_entry.ReadAppEntry
import com.mey.newsapp.domain.usecases.app_entry.SaveAppEntry
import com.mey.newsapp.domain.usecases.news.DeleteArticle
import com.mey.newsapp.domain.usecases.news.GetNews
import com.mey.newsapp.domain.usecases.news.NewsUseCases
import com.mey.newsapp.domain.usecases.news.SearchNews
import com.mey.newsapp.domain.usecases.news.SelectArticle
import com.mey.newsapp.domain.usecases.news.SelectArticles
import com.mey.newsapp.domain.usecases.news.UpsertArticle
import com.mey.newsapp.util.Constants.BASE_URL
import com.mey.newsapp.util.Constants.NEWS_DATABASE
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
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(newsApi, newsDao)


    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            selectArticle = SelectArticle(newsRepository)
        )
    }

    // ROOM
    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao


}