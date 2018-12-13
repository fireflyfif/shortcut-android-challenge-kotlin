package com.example.ivaivanova.myxkcd.ui.favfragment

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import com.example.ivaivanova.myxkcd.ui.comicsfragment.XkcdDiffUtilCallback

class FavComicsAdapter : PagedListAdapter<Comic, RecyclerView.ViewHolder> (XkcdDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavComicsViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.comic_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FavComicsViewHolder).bind(getItem(position))
    }

}