package com.example.ivaivanova.myxkcd.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.ivaivanova.myxkcd.data.XkcdRepository

/**
 * TODO: Not in use for now. Remove later!
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: XkcdRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComicsViewModel::class.java)) {
            return ComicsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}