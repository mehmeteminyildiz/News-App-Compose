package com.mey.newsapp.domain.repository

import androidx.paging.PagingData
import com.mey.newsapp.domain.model.Articles
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(
        sources: List<String>
    ): Flow<PagingData<Articles>>


    fun searchNews(
        searchQuery: String,
        sources: List<String>
    ): Flow<PagingData<Articles>>

    suspend fun upsertArticle(articles: Articles)

    suspend fun deleteArticle(articles: Articles)

    suspend fun selectArticles(): Flow<List<Articles>>

    suspend fun selectArticle(url: String): Articles?


}