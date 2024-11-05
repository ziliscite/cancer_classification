package com.dicoding.asclepius.repository

import com.dicoding.asclepius.data.dto.ArticlesItem
import com.dicoding.asclepius.data.local.ClassificationDao
import com.dicoding.asclepius.data.remote.ApiService
import com.dicoding.asclepius.helper.ClassificationResponse
import java.io.IOException

class RemoteRepository(
    private val apiService: ApiService
) {
    suspend fun getArticles(): ClassificationResponse<List<ArticlesItem>> {
        return try {
            val response = apiService.getArticles()

            if (response.status != "ok") {
                throw Exception("Something went wrong")
            }

            // Null
            response.articles?.let {
                return@let ClassificationResponse.Success(it)
            } ?: throw Exception("No articles found")

            // Empty
            if (response.articles.isEmpty()) {
                throw Exception("No articles found")
            }

            ClassificationResponse.Success(response.articles)
        } catch (e: IOException) {
            ClassificationResponse.Error("Internet connection problem")
        } catch (e: Exception) {
            ClassificationResponse.Error(e.message.toString())
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