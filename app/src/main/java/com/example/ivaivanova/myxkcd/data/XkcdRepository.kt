package com.example.ivaivanova.myxkcd.data

import com.example.ivaivanova.myxkcd.database.ComicsDao
import com.example.ivaivanova.myxkcd.model.Comic
import java.util.concurrent.Executor

/**
 * Repository class that works with local data sources.
 *
 */
class XkcdRepository(
    private val comicsDao: ComicsDao,
    private val executor: Executor
) {

    /**
     * Function for getting all comics from the cache
     */
    //fun getComics() = cache.allComics()


    /**
     * Function for inserting a favorite comic into the database
     */
    fun insertItem(favComic: Comic) {
        executor.execute { comicsDao.insertComic(favComic) }
    }

}