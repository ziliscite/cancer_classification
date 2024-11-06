package com.dicoding.asclepius.data.remote

import com.dicoding.asclepius.data.dto.Articles
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("q") q: String = "cancer",
        @Query("language") language: String = "en",
        @Query("category") category: String = "health",
    ): Articles
}
