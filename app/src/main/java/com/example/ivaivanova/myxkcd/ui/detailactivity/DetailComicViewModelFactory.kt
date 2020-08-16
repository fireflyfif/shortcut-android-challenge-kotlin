package com.example.ivaivanova.myxkcd.ui.detailactivity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.ivaivanova.myxkcd.data.XkcdRepository


class DetailComicViewModelFactory(private val repository: XkcdRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailComicViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailComicViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}