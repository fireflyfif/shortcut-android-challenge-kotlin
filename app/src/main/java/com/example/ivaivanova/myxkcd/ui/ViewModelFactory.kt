package com.example.ivaivanova.myxkcd.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.ivaivanova.myxkcd.data.XkcdRepository

class ViewModelFactory(private val repository: XkcdRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComicsViewModel::class.java)) {
            return ComicsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}