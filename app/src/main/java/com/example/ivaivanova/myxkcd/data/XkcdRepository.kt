package com.example.ivaivanova.myxkcd.data

import com.example.ivaivanova.myxkcd.api.XkcdService
import com.example.ivaivanova.myxkcd.database.XkcdLocalCache
import java.lang.reflect.Constructor

/**
 * Repository class that works with local and remote data sources.
 */
class XkcdRepository (
    private val service: XkcdService,
    private val cache: XkcdLocalCache
) {

    fun getComics() = cache.allComics()


    companion object {

        @Volatile
        private var INSTANCE: XkcdRepository? = null

        fun getInstance(service: XkcdService, cache: XkcdLocalCache) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: XkcdRepository(service, cache).also { INSTANCE = it }
                }
    }

    // TODO: How to implement the logic in this Repository class?

}