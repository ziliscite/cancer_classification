package com.dicoding.asclepius.repository

import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.dto.ClassificationResult
import com.dicoding.asclepius.data.local.ClassificationDao

class LocalRepository(
    private val classificationDao: ClassificationDao
) {
    fun getAll(): LiveData<List<ClassificationResult>> {
        return classificationDao.getAll()
    }

    fun getById(id: Int): LiveData<ClassificationResult> {
        return classificationDao.getResultById(id)
    }

    suspend fun upsert(classificationResult: ClassificationResult) {
        classificationDao.upsert(classificationResult)
    }

    suspend fun delete(id: Int) {
        classificationDao.delete(id)
    }

    companion object {
        @Volatile
        private var instance: LocalRepository? = null

        fun getInstance(dao: ClassificationDao): LocalRepository {
            return instance ?: synchronized(this) {
                instance ?: LocalRepository(dao)
            }.also {
                instance = it
            }
        }
    }
}