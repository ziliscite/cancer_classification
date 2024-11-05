package com.dicoding.asclepius.view.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.dto.ClassificationResult
import com.dicoding.asclepius.repository.LocalRepository
import kotlinx.coroutines.launch

class ResultViewModel(
    private val localRepository: LocalRepository
) : ViewModel() {
    fun upsert(classificationResult: ClassificationResult) { viewModelScope.launch {
        localRepository.upsert(classificationResult)
    }}

    fun delete(id: Int) { viewModelScope.launch {
        localRepository.delete(id)
    }}
}
