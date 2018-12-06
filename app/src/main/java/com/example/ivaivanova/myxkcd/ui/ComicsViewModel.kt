package com.example.ivaivanova.myxkcd.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.ivaivanova.myxkcd.database.ComicsDb
import com.example.ivaivanova.myxkcd.model.Comic
import com.example.ivaivanova.myxkcd.utils.Injection

/**
 * ViewModel for the Comics class
 * NT: Do not hold an instance of the context here!!!
 */
class ComicsViewModel : ViewModel() {

    var comicsResult: LiveData<PagedList<Comic>>


    init {
        // COMPLETED: Move this in the ViewModel later
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        comicsResult = initializedPagedListBuilder(config).build()
    }


    private fun initializedPagedListBuilder(config: PagedList.Config) :
            LivePagedListBuilder<Int, Comic> {

        // TODO: Can't create the database here, because it requires context!!!
        //val database = Injection.provideCache()

        val dataSourceFactory = object : DataSource.Factory<Int, Comic>() {
            override fun create(): DataSource<Int, Comic> {
                return XkcdDataSource()
            }
        }

        return LivePagedListBuilder<Int, Comic>(dataSourceFactory, config)
    }

    //fun currentComic: LiveData<PagedList<Comic>> {return comicsResult}
}