package com.mey.newsapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "news")
data class Articles(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String,
    @PrimaryKey val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
): Parcelable