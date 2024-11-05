package com.dicoding.asclepius.helper

sealed class ClassificationResponse<out R> private constructor() {
    data class Success<out T>(val data: T) : ClassificationResponse<T>()
    data class Error(val error: String) : ClassificationResponse<Nothing>()
    data object Loading : ClassificationResponse<Nothing>()
}
