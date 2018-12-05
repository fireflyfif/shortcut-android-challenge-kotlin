package com.example.ivaivanova.myxkcd.ui

import android.arch.paging.PagedListAdapter
import android.view.ViewGroup
import com.example.ivaivanova.myxkcd.model.Comic

class XkcdAdapter : PagedListAdapter<Comic, XkcdViewHolder> (XkcdDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XkcdViewHolder {
        return XkcdViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: XkcdViewHolder, position: Int) {
        val comicItem = getItem(position)
        if (comicItem != null) {
            // Bind the views within the item
            holder.bind(comicItem)
        }
    }


}

