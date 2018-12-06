package com.example.ivaivanova.myxkcd.data

import com.example.ivaivanova.myxkcd.api.XkcdService
import com.example.ivaivanova.myxkcd.database.XkcdLocalCache

/**
 * Repository class that works with local and remote data sources.
 */
class XkcdRepository(
    private val service: XkcdService,
    private val cache: XkcdLocalCache
) {

}