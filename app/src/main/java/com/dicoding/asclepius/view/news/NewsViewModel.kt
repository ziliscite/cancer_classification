package com.dicoding.asclepius.view.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.dto.ArticlesItem
import com.dicoding.asclepius.helper.NewsResponse
import com.dicoding.asclepius.repository.RemoteRepository
import kotlinx.coroutines.launch

class NewsViewModel(
    private val remoteRepository: RemoteRepository
) : ViewModel() {
    private val _news = MutableLiveData<NewsResponse<List<ArticlesItem>>>()
    val news: LiveData<NewsResponse<List<ArticlesItem>>> get() = _news

    init {
        getNews()
    }

    private fun getNews() { viewModelScope.launch {
        if (news.value.let { it is NewsResponse.Success }) {
            return@launch
        }

        _news.postValue(NewsResponse.Loading)
        when (val response = remoteRepository.getNews()) {
            is NewsResponse.Success -> {
                _news.postValue(NewsResponse.Success(response.data))
            }

            is NewsResponse.Error -> {
                _news.postValue(NewsResponse.Error(response.error))
            }

            is NewsResponse.Loading -> {
                _news.postValue(NewsResponse.Loading)
            }
        }
    }}
}