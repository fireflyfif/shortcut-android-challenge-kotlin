package com.example.ivaivanova.myxkcd.data

import com.example.ivaivanova.myxkcd.api.XkcdService
import com.example.ivaivanova.myxkcd.database.XkcdLocalCache

/**
 * Repository class that works with local and remote data sources.
 * TODO: Not in use for now. Remove later!
 */
class XkcdRepository (
    private val service: XkcdService,
    private val cache: XkcdLocalCache
) {

    /**
     * Function for getting all comics from the cache
     */
    fun getComics() = cache.allComics()


}