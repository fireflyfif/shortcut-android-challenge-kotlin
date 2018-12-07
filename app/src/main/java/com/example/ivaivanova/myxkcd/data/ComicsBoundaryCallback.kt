package com.example.ivaivanova.myxkcd.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.example.ivaivanova.myxkcd.api.XkcdService
import com.example.ivaivanova.myxkcd.api.makeQuery
import com.example.ivaivanova.myxkcd.database.XkcdLocalCache
import com.example.ivaivanova.myxkcd.model.Comic

/**
 * Boundary Callback is the handler when PagedList reaches the “out of data” condition.
 * This new class resolves a requirement for maintaining visible lists and loaded data by
 * developers. When a PagedList displays the last item in its DataSource instance, it'll
 * emit a trigger to run onItemAtEndLoaded() method.
 *
 * Source: https://www.captechconsulting.com/blogs/an-overview-of-android-jetpack
 */
class ComicsBoundaryCallback(
    private val service: XkcdService,
    private val cache: XkcdLocalCache
) : PagedList.BoundaryCallback<Comic>() {

    private var lastRequestedComics = 1

    private var isRequestProgress = false

    private val _networkErrors = MutableLiveData<String>()

    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()

        // fetch data from service
        requestAndSaveData()
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtFrontLoaded(itemAtFront: Comic) {
        super.onItemAtFrontLoaded(itemAtFront)
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestProgress) return

        isRequestProgress = true

        makeQuery(
            service,
            lastRequestedComics,
            { comics ->
                cache.insert(comics) {
                    // TODO: Do not increment the number of comics, but rather decrement
                    lastRequestedComics++
                    isRequestProgress = false
                }
            },
            {error ->
            _networkErrors.postValue(error)
            isRequestProgress = false
        })
    }
}