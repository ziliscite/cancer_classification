package com.dicoding.asclepius.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.dto.ClassificationResult
import com.dicoding.asclepius.repository.LocalRepository

class HistoryViewModel(
    private val repository: LocalRepository
) : ViewModel() {

    fun getHistories(): LiveData<List<ClassificationResult>> {
        return repository.getAll()
    }
}
