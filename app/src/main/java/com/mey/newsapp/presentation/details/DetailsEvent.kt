package com.mey.newsapp.presentation.details

import com.mey.newsapp.domain.model.Articles
import com.mey.newsapp.domain.usecases.news.SelectArticle

sealed class DetailsEvent {

    data class UpsertDeleteArticle(val article: Articles): DetailsEvent()

    object RemoveSideEffect: DetailsEvent()
}