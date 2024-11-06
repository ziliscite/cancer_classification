package com.dicoding.asclepius.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.di.Injection
import com.dicoding.asclepius.repository.LocalRepository
import com.dicoding.asclepius.view.history.HistoryViewModel
import com.dicoding.asclepius.view.result.ResultViewModel

class LocalViewModelFactory private constructor(
    private val localRepository: LocalRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(localRepository) as T
        } else if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(localRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: LocalViewModelFactory? = null

        fun getInstance(context: Context): LocalViewModelFactory {
            return instance ?: synchronized(this) {
                val localRepository = Injection.provideLocalRepository(context)
                instance ?: LocalViewModelFactory(localRepository)
            }.also {
                instance = it
            }
        }
    }
}
