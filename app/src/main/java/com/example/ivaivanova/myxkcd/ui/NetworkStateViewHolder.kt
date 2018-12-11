package com.example.ivaivanova.myxkcd.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.utils.NetworkState
import com.example.ivaivanova.myxkcd.utils.Status

/**
 * A View Holder that can display a loading or click action.
 * It is used to show the network state of paging.
 *
 * Source: https://github.com/googlesamples/android-architecture-components/tree/master/PagingWithNetworkSample
 */
class NetworkStateViewHolder(
    view: View,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(view) {

    private val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
    private val retryButton: Button = view.findViewById(R.id.retry_button)
    private val errorMessage: TextView = view.findViewById(R.id.error_message)

    init {
        retryButton.setOnClickListener { retryCallback }
    }

    fun bindTo(networkState: NetworkState?) {
        //progressBar.visibility = visibility(networkState?.status == Status.RUNNING)
        //retryButton.visibility = visibility(networkState?.status == Status.FAILED)

        // TODO: This visibility state is temporary. Switch to the above code after this is fixed
        progressBar.visibility = View.VISIBLE
        retryButton.visibility = View.VISIBLE


        errorMessage.visibility = visibility(networkState?.msg != null)
        errorMessage.text = networkState?.msg
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.network_state_item, parent, false)
            return NetworkStateViewHolder(view, retryCallback)
        }

        fun visibility(show: Boolean): Int {
            return if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}