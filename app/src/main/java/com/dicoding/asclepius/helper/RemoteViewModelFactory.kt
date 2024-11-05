package com.dicoding.asclepius.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.di.Injection
import com.dicoding.asclepius.repository.RemoteRepository
import com.dicoding.asclepius.view.news.NewsViewModel

class RemoteViewModelFactory private constructor(
    private val remoteRepository: RemoteRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(remoteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: RemoteViewModelFactory? = null

        fun getInstance(): RemoteViewModelFactory {
            return instance ?: synchronized(this) {
                val remoteRepository = Injection.provideRemoteRepository()
                instance ?: RemoteViewModelFactory(remoteRepository)
            }.also {
                instance = it
            }
        }
    }
}
