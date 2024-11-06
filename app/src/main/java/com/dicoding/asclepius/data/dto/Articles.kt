package com.dicoding.asclepius.data.dto

import com.google.gson.annotations.SerializedName

data class Articles(
    @field:SerializedName("totalResults")
    val totalResults: Int,

    @field:SerializedName("articles")
    val articles: List<ArticlesItem>,

    @field:SerializedName("status")
    val status: String,
)

data class ArticlesItem(
    @field:SerializedName("urlToImage")
    val urlToImage: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("url")
    val url: String,
)