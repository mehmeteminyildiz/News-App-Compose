package com.mey.newsapp.domain.usecases.news

import com.mey.newsapp.data.local.NewsDao
import com.mey.newsapp.domain.model.Articles
import com.mey.newsapp.domain.repository.NewsRepository


class DeleteArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(articles: Articles) {
        newsRepository.deleteArticle(articles)
    }
}