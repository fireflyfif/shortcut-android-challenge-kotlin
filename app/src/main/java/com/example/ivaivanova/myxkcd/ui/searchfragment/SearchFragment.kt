package com.example.ivaivanova.myxkcd.ui.searchfragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.example.ivaivanova.myxkcd.R
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        rootView.search_webview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl("https://relevantxkcd.appspot.com/")
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                rootView.web_progress_bar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                rootView.web_progress_bar.visibility = View.GONE
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                rootView.web_progress_bar.visibility = View.GONE
                super.onReceivedError(view, request, error)
            }
        }

        val webSettings: WebSettings = rootView.search_webview.settings
        webSettings.javaScriptEnabled = true

        rootView.search_webview.settings.useWideViewPort = true
        rootView.search_webview.settings.setSupportZoom(true)
        rootView.search_webview.settings.useWideViewPort = true
        rootView.search_webview.settings.loadsImagesAutomatically = true
        rootView.search_webview.settings.javaScriptCanOpenWindowsAutomatically = true

        rootView.search_webview.loadUrl("https://relevantxkcd.appspot.com/")
        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ComicsFragment.
         */
        fun newInstance(): SearchFragment = SearchFragment()
    }
}