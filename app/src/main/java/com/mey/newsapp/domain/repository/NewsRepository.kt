package com.mey.newsapp.domain.repository

import androidx.paging.PagingData
import com.mey.newsapp.domain.model.Articles
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(
        sources: List<String>
    ): Flow<PagingData<Articles>>
}