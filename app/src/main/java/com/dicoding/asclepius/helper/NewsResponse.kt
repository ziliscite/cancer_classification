package com.dicoding.asclepius.helper

sealed class NewsResponse<out R> private constructor() {
    data class Success<out T>(val data: T) : NewsResponse<T>()
    data class Error(val error: String) : NewsResponse<Nothing>()
    data object Loading : NewsResponse<Nothing>()
}
