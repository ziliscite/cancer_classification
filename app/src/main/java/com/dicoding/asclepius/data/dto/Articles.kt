package com.dicoding.asclepius.data.dto

import com.google.gson.annotations.SerializedName

data class Articles(
    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<ArticlesItem>? = null,

    @field:SerializedName("status")
    val status: String? = null,
)

data class ArticlesItem(
    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("url")
    val url: String? = null,
)