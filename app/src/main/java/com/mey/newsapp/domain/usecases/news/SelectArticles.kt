package com.mey.newsapp.domain.usecases.news

import com.mey.newsapp.data.local.NewsDao
import com.mey.newsapp.domain.model.Articles
import com.mey.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow


class SelectArticles(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): Flow<List<Articles>> {
        return newsRepository.selectArticles()
    }
}