package com.mey.newsapp.domain.usecases.news

import com.mey.newsapp.data.local.NewsDao
import com.mey.newsapp.domain.model.Articles
import com.mey.newsapp.domain.repository.NewsRepository

class SelectArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(url: String): Articles? {
        return newsRepository.selectArticle(url)
    }
}