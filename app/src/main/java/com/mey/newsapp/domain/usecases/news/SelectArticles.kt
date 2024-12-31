package com.mey.newsapp.domain.usecases.news

import com.mey.newsapp.data.local.NewsDao
import com.mey.newsapp.domain.model.Articles
import kotlinx.coroutines.flow.Flow


class SelectArticles(
    private val newsDao: NewsDao
) {
    operator fun invoke(): Flow<List<Articles>> {
        return newsDao.getArticles()
    }
}