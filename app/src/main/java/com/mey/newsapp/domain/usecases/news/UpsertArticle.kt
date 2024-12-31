package com.mey.newsapp.domain.usecases.news

import com.mey.newsapp.data.local.NewsDao
import com.mey.newsapp.domain.model.Articles

class UpsertArticle(
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(articles: Articles) {
        newsDao.upsert(articles)
    }
}