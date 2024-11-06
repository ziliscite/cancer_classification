package com.dicoding.asclepius.repository

import com.dicoding.asclepius.data.dto.ArticlesItem
import com.dicoding.asclepius.data.remote.ApiService
import com.dicoding.asclepius.helper.NewsResponse
import java.io.IOException

class RemoteRepository(
    private val apiService: ApiService
) {
    suspend fun getNews(): NewsResponse<List<ArticlesItem>> {
        return try {
            val response = apiService.getNews()

            if (response.status != "ok") {
                throw Exception("Something went wrong")
            }

            // Empty
            if (response.articles.isEmpty()) {
                throw Exception("No articles found")
            }

            NewsResponse.Success(response.articles)
        } catch (e: IOException) {
            NewsResponse.Error("Internet connection problem")
        } catch (e: Exception) {
            NewsResponse.Error(e.message.toString())
        }
    }

    companion object {
        @Volatile
        private var instance: RemoteRepository? = null

        fun getInstance(apiService: ApiService): RemoteRepository {
            return instance ?: synchronized(this) {
                instance ?: RemoteRepository(apiService)
            }.also {
                instance = it
            }
        }
    }
}