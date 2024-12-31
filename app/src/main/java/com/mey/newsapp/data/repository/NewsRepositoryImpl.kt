package com.mey.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mey.newsapp.data.local.NewsDao
import com.mey.newsapp.data.remote.NewsApi
import com.mey.newsapp.data.remote.NewsPagingSource
import com.mey.newsapp.data.remote.SearchNewsPagingSource
import com.mey.newsapp.domain.model.Articles
import com.mey.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
): NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Articles>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Articles>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override suspend fun upsertArticle(articles: Articles) {
        newsDao.upsert(articles)
    }

    override suspend fun deleteArticle(articles: Articles) {
        newsDao.delete(articles)
    }

    override suspend fun selectArticles(): Flow<List<Articles>> {
        return newsDao.getArticles().map { it.reversed() }
    }

    override suspend fun selectArticle(url: String): Articles? {
        return newsDao.getArticle(url)
    }
}