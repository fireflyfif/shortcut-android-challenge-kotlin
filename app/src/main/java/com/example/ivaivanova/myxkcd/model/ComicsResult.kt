package com.example.ivaivanova.myxkcd.model

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

/**
 * TODO: Not sure if I need this
 */
data class ComicsResult(
    val data: LiveData<PagedList<Comic>>,
    val networkErrors: LiveData<String>
)