package com.mey.newsapp.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mey.newsapp.domain.model.Articles
import com.mey.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel
@Inject constructor(private val newsUseCases: NewsUseCases) : ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = newsUseCases.selectArticle(event.article.url)
                    article?.let {
                        deleteArticle(event.article)
                    } ?: run {
                        upsertArticle(event.article)
                    }
                }
            }

            DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    private suspend fun upsertArticle(article: Articles) {
        newsUseCases.upsertArticle(articles = article)
        sideEffect = "Article Saved"
    }

    private suspend fun deleteArticle(article: Articles) {
        newsUseCases.deleteArticle(articles = article)
        sideEffect = "Article Deleted"
    }


}