package com.mey.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mey.newsapp.domain.model.Articles
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(articles: Articles): Long

    @Delete
    suspend fun delete(articles: Articles): Int

    @Query("SELECT * FROM news")
    fun getArticles(): Flow<List<Articles>>

    @Query("SELECT * FROM news WHERE url=:url")
    suspend fun getArticle(url: String): Articles?

}