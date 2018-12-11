package com.example.ivaivanova.myxkcd.ui

import android.support.v7.util.DiffUtil
import com.example.ivaivanova.myxkcd.model.Comic

class XkcdDiffUtilCallback : DiffUtil.ItemCallback<Comic>() {

    override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
        return oldItem.num == (newItem.num)
    }

    override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
        return oldItem.num == (newItem.num)
    }
}