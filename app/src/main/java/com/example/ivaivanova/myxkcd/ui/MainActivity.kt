package com.example.ivaivanova.myxkcd.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import com.example.ivaivanova.myxkcd.utils.Injection
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val comicsAdapter = XkcdAdapter()
    // TODO: To understand when to use lateinit variable and when not to
    private lateinit var viewModel: ComicsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the View Model
        viewModel = ViewModelProviders.of(this).get(ComicsViewModel::class.java)

        initAdapter()
        initializeViewModel()
    }

    private fun initAdapter() {
        val columnCount: Int = resources.getInteger(R.integer.list_column_count)

        comics_rv.layoutManager = StaggeredGridLayoutManager(columnCount,
            StaggeredGridLayoutManager.VERTICAL)
        comics_rv.adapter = comicsAdapter
    }


    private fun initializeViewModel() {
        viewModel.comicsResult.observe(this, Observer {
            comicsAdapter.submitList(it)
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
