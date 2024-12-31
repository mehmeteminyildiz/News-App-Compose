package com.mey.newsapp.presentation.bookmark

import com.mey.newsapp.domain.model.Articles

data class BookmarkState(
    val articles : List<Articles> = emptyList()
)