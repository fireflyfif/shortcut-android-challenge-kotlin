package com.example.ivaivanova.myxkcd.ui.comicsfragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.ivaivanova.myxkcd.model.Comic
import com.example.ivaivanova.myxkcd.utils.NetworkState

/**
 * ViewModel for the Comics class
 * NT: Do not hold an instance of the context here!!!
 */
class ComicsViewModel : ViewModel() {

    val comicLiveData = MutableLiveData<XkcdDataSource>()

    var comicsResult: LiveData<PagedList<Comic>>
    private val comicsDataSourceFactory: XkcdDataSourceFactory = XkcdDataSourceFactory()

    lateinit var networkState: LiveData<NetworkState>
    lateinit var loadingState: LiveData<NetworkState>


    init {
        // TODO: Question: I have no idea what does switchMap do!!!
        // TODO: Show the network state when there is a problem with the network
        //networkState = switchMap(comicName) { networkState }
        //loadingState = switchMap(comicName) { loadingState }

        //networkState = switchMap(comicLiveData) { input -> networkState }
        //loadingState = switchMap(comicLiveData) { input -> networkState }

        // Configure the PagedList
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        comicsResult = LivePagedListBuilder<Int, Comic>(comicsDataSourceFactory, config)
            .build()
    }

    fun getState(): LiveData<NetworkState> =
        switchMap(
            comicsDataSourceFactory.comicsDataSourceLiveData,
            XkcdDataSource::netState
        )


    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Comic> {

        val dataSourceFactory = object : DataSource.Factory<Int, Comic>() {
            override fun create(): DataSource<Int, Comic> {
                return XkcdDataSource()
            }
        }

        return LivePagedListBuilder<Int, Comic>(dataSourceFactory, config)
    }

    fun refreshData() {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        comicsResult.value?.config
        comicsResult = LivePagedListBuilder<Int, Comic>(comicsDataSourceFactory, config)
            .build()
    }

    fun listIsEmpty(): Boolean {
        return comicsResult.value?.isEmpty() ?: true
    }
}