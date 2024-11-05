package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.local.ClassificationDatabase
import com.dicoding.asclepius.data.remote.ApiConfig
import com.dicoding.asclepius.repository.LocalRepository
import com.dicoding.asclepius.repository.RemoteRepository

object Injection {
    fun provideLocalRepository(context: Context): LocalRepository {
        val classificationDao = ClassificationDatabase.getDatabase(context).classificationDao()
        return LocalRepository.getInstance(classificationDao)
    }

    fun provideRemoteRepository(): RemoteRepository {
        val apiService = ApiConfig.getApiService()
        return RemoteRepository.getInstance(apiService)
    }
}