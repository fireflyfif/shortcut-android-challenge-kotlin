package com.example.ivaivanova.myxkcd.ui

import com.example.ivaivanova.myxkcd.model.Comic
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.example.ivaivanova.myxkcd.api.XkcdService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class XkcdDataSource : PageKeyedDataSource<String, Comic>() {

    private val api = XkcdService.create()
    var comicId: Int? = 0


    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Comic>) {

        api.getCurrentComic().enqueue(object : Callback<Comic> {

            val comicList = mutableListOf<Comic>()

            override fun onResponse(call: Call<Comic>?, response: Response<Comic>) {
                // TODO: Why this variable needs to be save for nulls?
                val currentComic: Comic? = response.body()
                comicList.add(currentComic!!)
                //val comicTitle = currentComic?.safeTitle?.map { //it. }
                comicId = comicId!! - 1

                callback.onResult(comicList, null, comicId?.toString())

            }

            override fun onFailure(call: Call<Comic>?, t: Throwable?) {
                Log.e("XkcdDataSource", "Failed to fetch data.")
            }
        })
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, Comic>) {

        //api.getComicById()
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, Comic>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}