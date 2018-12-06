package com.example.ivaivanova.myxkcd.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComicsViewModel::class.java)) {
            return ComicsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}