package com.example.ivaivanova.myxkcd.database

import android.arch.paging.DataSource
import com.example.ivaivanova.myxkcd.model.Comic
import java.util.concurrent.Executor

/**
 * Class that handles the DAO local data source. This ensures that methods are
 * triggered on the correct executor.
 *
 * Source: https://codelabs.developers.google.com/codelabs/android-paging/index.html?index=..%2F..index#0
 * TODO: Not in use for now. Remove later!
 */
class XkcdLocalCache(
    private val comicDao: ComicsDao,
    private val ioExecutor: Executor
) {


    /**
     * Insert a list of comics in the database, on a background thread.
     * Remove if not used!
     */
    fun insert(comicsList: List<Comic>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            //comicDao.insertComic(comicsList)
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