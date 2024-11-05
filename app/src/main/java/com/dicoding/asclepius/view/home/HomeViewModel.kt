package com.dicoding.asclepius.view.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeViewModel : ViewModel() {
    private val _uri = MutableLiveData<Uri?>()
    val uri: LiveData<Uri?> get() = _uri

    fun setUri(uri: Uri?) {
        _uri.value = uri
    }
}