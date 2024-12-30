package com.mey.newsapp.presentation.search

import androidx.paging.PagingData
import com.mey.newsapp.domain.model.Articles
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Articles>>?=null
) {
}