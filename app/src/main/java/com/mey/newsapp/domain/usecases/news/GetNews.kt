package com.mey.newsapp.domain.usecases.news

import androidx.paging.PagingData
import com.mey.newsapp.domain.model.Articles
import com.mey.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<Articles>> {
        return newsRepository.getNews(sources)
    }
}