package com.mey.newsapp.domain.usecases.news

import androidx.paging.PagingData
import com.mey.newsapp.domain.model.Articles
import com.mey.newsapp.domain.repository.NewsRepository
import com.mey.newsapp.presentation.search.SearchEvent
import kotlinx.coroutines.flow.Flow

class SearchNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Articles>> {
        return newsRepository.searchNews(searchQuery, sources)
    }
}