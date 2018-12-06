package com.example.ivaivanova.myxkcd.utils

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.ivaivanova.myxkcd.api.XkcdService
import com.example.ivaivanova.myxkcd.data.XkcdRepository
import com.example.ivaivanova.myxkcd.database.ComicsDb
import com.example.ivaivanova.myxkcd.database.XkcdLocalCache
import com.example.ivaivanova.myxkcd.ui.ViewModelFactory
import java.util.concurrent.Executors

/**
 * Object that handles object creation.
 *
 * Source: https://codelabs.developers.google.com/codelabs/android-paging/index.html?index=..%2F..index#8
 */
object Injection {

    fun provideCache(context: Context): XkcdLocalCache {
        val database = ComicsDb.getInstance(context)
        return XkcdLocalCache(database.comicsDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideXkcdRepository(context: Context): XkcdRepository {
        return XkcdRepository(XkcdService.create(), provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory()
    }
}