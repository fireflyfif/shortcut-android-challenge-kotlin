package com.example.ivaivanova.myxkcd.ui.favfragment

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.ivaivanova.myxkcd.data.XkcdRepository
import java.lang.IllegalArgumentException

class FavComicsViewModelFactory(private val repository: XkcdRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavComicsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavComicsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}