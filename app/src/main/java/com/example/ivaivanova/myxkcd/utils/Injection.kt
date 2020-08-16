package com.example.ivaivanova.myxkcd.utils

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.ivaivanova.myxkcd.data.XkcdRepository
import com.example.ivaivanova.myxkcd.database.ComicsDb
import com.example.ivaivanova.myxkcd.ui.detailactivity.DetailComicViewModelFactory
import com.example.ivaivanova.myxkcd.ui.favfragment.FavComicsViewModelFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Object that handles object creation.
 *
 * Source: https://codelabs.developers.google.com/codelabs/android-paging/index.html?index=..%2F..index#8
 */
object Injection {

    /*private fun provideCache(context: Context): XkcdLocalCache {
        val database = ComicsDb.getInstance(context)
        return XkcdLocalCache(database.comicsDao(), Executors.newSingleThreadExecutor())
    }*/

    private fun provideXkcdRepository(context: Context): XkcdRepository {
        val database = ComicsDb.getInstance(context)
        return XkcdRepository(database.comicsDao(), Executors.newSingleThreadExecutor())
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return FavComicsViewModelFactory(provideXkcdRepository(context))
    }

    fun provideDetailViewModelFactory(context: Context): ViewModelProvider.Factory {
        return DetailComicViewModelFactory(provideXkcdRepository(context))
    }
}