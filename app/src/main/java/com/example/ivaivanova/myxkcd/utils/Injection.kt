package com.example.ivaivanova.myxkcd.utils

import android.content.Context
import com.example.ivaivanova.myxkcd.database.ComicsDb
import com.example.ivaivanova.myxkcd.database.XkcdLocalCache
import java.util.concurrent.Executors

/**
 * Object that handles object creation.
 *
 * Source: https://codelabs.developers.google.com/codelabs/android-paging/index.html?index=..%2F..index#8
 */
object Injection {

    private fun provideCache(context: Context): XkcdLocalCache {
        val database = ComicsDb.getInstance(context)
        return XkcdLocalCache(database.comicsDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideXkcdRepository(context: Context): XkcdRepository {

    }
}