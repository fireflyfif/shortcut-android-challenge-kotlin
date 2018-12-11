package com.example.ivaivanova.myxkcd.ui

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import com.example.ivaivanova.myxkcd.utils.NetworkState
import java.lang.IllegalArgumentException

class XkcdAdapter(
    private val retryCallback: () -> Unit,
    private val listener: (Comic?) -> Unit)
    : PagedListAdapter<Comic, RecyclerView.ViewHolder> (XkcdDiffUtilCallback()) {

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.comic_item -> XkcdViewHolder.create(parent)
            // TODO: Check why is it not switching to this layout when there is no net
            R.layout.network_state_item -> NetworkStateViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        // Bind the views within the item according to the layout
        when (getItemViewType(position)) {
            R.layout.comic_item -> (holder as XkcdViewHolder).bind(getItem(position), listener)
            R.layout.network_state_item -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.comic_item
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val previousExtraRow = hasExtraRow()

        this.networkState = newNetworkState
        val newExtraRow = hasExtraRow()

        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if(newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}

