package com.mey.newsapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.mey.newsapp.domain.model.Articles

data class NewsResponse(
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Int? = null,
    @SerializedName("articles") var articles: ArrayList<Articles> = arrayListOf()
)