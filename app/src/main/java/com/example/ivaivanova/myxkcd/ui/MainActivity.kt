package com.example.ivaivanova.myxkcd.ui

import android.arch.lifecycle.Observer
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = XkcdAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeListTemp()

    }

    private fun initializeListTemp() {

        comics_rv.layoutManager = LinearLayoutManager(this)
        comics_rv.adapter = adapter

        // TODO: Move this in the ViewModel later
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        val liveData = initializedPagedListBuilder(config).build()

        // TODO: To understand better !!!
        liveData.observe(this, Observer<PagedList<Comic>> {
            pagedList -> adapter.submitList(pagedList)
        })
    }

    // TODO: This method uses DataSourceFactory straight away
    private fun initializedPagedListBuilder(config: PagedList.Config) :
            LivePagedListBuilder<Int, Comic> {

        val dataSourceFactory = object : DataSource.Factory<Int, Comic>() {
            override fun create(): DataSource<Int, Comic> {
                return XkcdDataSource()
            }
        }
        return LivePagedListBuilder<Int, Comic>(dataSourceFactory, config)

    }
}
