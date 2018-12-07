package com.example.ivaivanova.myxkcd.data

import android.arch.paging.LivePagedListBuilder
import com.example.ivaivanova.myxkcd.api.XkcdService
import com.example.ivaivanova.myxkcd.database.XkcdLocalCache
import com.example.ivaivanova.myxkcd.model.ComicsResult

/**
 * Repository class that works with local and remote data sources.
 */
class XkcdRepository (
    private val service: XkcdService,
    private val cache: XkcdLocalCache
) {

    /**
     * Function for getting all comics from the cache
     */
    fun getComics() = cache.allComics()


    fun getComicsFromBoundaryCallback(): ComicsResult {
        // Get data source factory from the local cache
        val dataSourceFactory = cache.allComics()

        // Construct the boundary callback
        val boundaryCallback = ComicsBoundaryCallback(service, cache)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, 10)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return ComicsResult(data, networkErrors)
    }

    companion object {

        @Volatile
        private var INSTANCE: XkcdRepository? = null

        fun getInstance(service: XkcdService, cache: XkcdLocalCache) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: XkcdRepository(service, cache).also { INSTANCE = it }
                }
    }


}