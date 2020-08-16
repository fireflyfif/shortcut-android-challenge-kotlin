package com.example.ivaivanova.myxkcd.ui.comicsfragment

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.example.ivaivanova.myxkcd.model.Comic

class XkcdDataSourceFactory : DataSource.Factory<Int, Comic>() {

    val comicsDataSourceLiveData = MutableLiveData<XkcdDataSource>()

    override fun create(): DataSource<Int, Comic> {
        val comicDataSource = XkcdDataSource()
        comicsDataSourceLiveData.postValue(comicDataSource)
        return comicDataSource
    }


}