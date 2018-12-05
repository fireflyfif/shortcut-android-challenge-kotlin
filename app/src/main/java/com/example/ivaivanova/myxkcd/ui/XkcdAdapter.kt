package com.example.ivaivanova.myxkcd.ui

import android.arch.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ivaivanova.myxkcd.model.Comic

class XkcdAdapter : PagedListAdapter<Comic, XkcdViewHolder> (XkcdDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XkcdViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.)
    }

    override fun onBindViewHolder(holder: XkcdViewHolder, position: Int) {

    }


}