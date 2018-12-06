package com.example.ivaivanova.myxkcd.database

import android.arch.paging.DataSource
import com.example.ivaivanova.myxkcd.model.Comic
import java.util.concurrent.Executor

/**
 * Class that handles the DAO local data source. This ensures that methods are
 * triggered on the correct executor.
 *
 * Source: https://codelabs.developers.google.com/codelabs/android-paging/index.html?index=..%2F..index#0
 */
class XkcdLocalCache(
    private val comicDao: ComicsDao,
    private val ioExecutor: Executor
) {

    // TODO: What do we do here with the second argument? Is it a function?
    /**
     * Insert a list of comics in the database, on a background thread.
     */
    fun insert(comisList: List<Comic>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            comicDao.insertComic(comisList)
            insertFinished()
        }
    }

    /**
     * Get a list of all comics from the Dao
     */
    fun allComics() : DataSource.Factory<Int, Comic> {
        return comicDao.getAllComics()
    }

}