package com.example.ivaivanova.myxkcd.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val comicsAdapter = XkcdAdapter()
    // TODO: To understand when to use lateinit variable and when not to
    private lateinit var viewModel: ComicsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializeListTemp()

        viewModel = ViewModelProviders.of(this)
            .get(ComicsViewModel::class.java)

        initAdapter()
        initializeViewModel()
    }

    private fun initAdapter() {
        var columnCount: Int = resources.getInteger(R.integer.list_column_count)

        comics_rv.layoutManager = StaggeredGridLayoutManager(columnCount,
            StaggeredGridLayoutManager.VERTICAL)
        comics_rv.adapter = comicsAdapter
    }

    private fun initializeViewModel() {
        viewModel.comicsResult.observe(this, Observer {
            comicsAdapter.submitList(it)
        })
    }


    private fun initializeListTemp() {

        // TODO: Move this in the ViewModel later
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        val liveData = initializedPagedListBuilder(config).build()

        // TODO: To understand better !!!
        liveData.observe(this, Observer<PagedList<Comic>> {
            pagedList -> comicsAdapter.submitList(pagedList)
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
