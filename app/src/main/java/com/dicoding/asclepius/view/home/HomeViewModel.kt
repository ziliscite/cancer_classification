package com.dicoding.asclepius.view.home

import android.net.Uri
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private var _uri: Uri? = null
    val uri get() = _uri

    fun setUri(uri: Uri?) {
        _uri = uri
    }
}
