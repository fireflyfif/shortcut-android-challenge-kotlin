package com.example.ivaivanova.myxkcd.data

import android.arch.paging.DataSource
import com.example.ivaivanova.myxkcd.database.ComicsDao
import com.example.ivaivanova.myxkcd.model.Comic
import java.util.concurrent.Executor

/**
 * Repository class that works with local data sources.
 * TODO: Question - Should I make this class a singleton?
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

    fun getAllFavs(): DataSource.Factory<Int, Comic> {
        return comicsDao.getAllComics()
    }

    /**
     * Function for inserting a favorite comic into the database
     */
    fun insertItem(favComic: Comic?) {
        executor.execute { comicsDao.insertComic(favComic) }
    }

    /**
     * Functiuon for deleting a favorite comic from the database
     */
    fun deleteItem(comicNumber: String) {
        executor.execute {
            comicsDao.deleteComic(comicNumber)
        }
    }

}