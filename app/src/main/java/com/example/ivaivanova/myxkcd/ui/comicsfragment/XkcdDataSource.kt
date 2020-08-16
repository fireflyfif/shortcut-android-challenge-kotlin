package com.example.ivaivanova.myxkcd.ui.comicsfragment

import android.arch.lifecycle.MutableLiveData
import com.example.ivaivanova.myxkcd.model.Comic
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.example.ivaivanova.myxkcd.api.XkcdService
import com.example.ivaivanova.myxkcd.utils.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A data source that uses the next comic number for paging through all xkcd comics
 *
 * TODO: Q- Should I put the ApiService within the scope of the class or should it stay as a private variable?
 */
class XkcdDataSource : PageKeyedDataSource<Int, Comic>() {

    //val networkState = MutableLiveData<NetworkState>()
    //val loadingState = MutableLiveData<NetworkState>()

    var netState: MutableLiveData<NetworkState> = MutableLiveData()

    private val api = XkcdService.create()
    var comicId: Int = 0


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Comic>
    ) {

        // Update NetworkState
        updateState(NetworkState.LOADING)
        //loadingState.postValue(NetworkState.LOADING)

        api.getCurrentComic().enqueue(object : Callback<Comic> {

            val comicList = mutableListOf<Comic>()

            override fun onResponse(call: Call<Comic>?, response: Response<Comic>) {
                // TODO: Why this variable needs to be null safe?
                if (response.isSuccessful) {
                    val currentComic: Comic? = response.body()
                    comicList.add(currentComic!!)

                    // Get the recent comic number
                    comicId = currentComic.num
                    comicId -= 1
                    Log.d("XkcdDataSource", "Current comic ID is $comicId")

                    callback.onResult(comicList, null, comicId)

                    updateState(NetworkState.LOADED)
                    //loadingState.postValue(NetworkState.LOADED)

                } else {

                    updateState(NetworkState.FAILED)
                }
            }

            override fun onFailure(call: Call<Comic>?, t: Throwable?) {
                Log.e("XkcdDataSource", "Failed to fetch data.")
                updateState(NetworkState.FAILED)
                //updateState(NetworkState.error("Error message ${t?.message}"))
            }
        })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Comic>
    ) {

        // Update NetworkState
        updateState(NetworkState.LOADING)
        //loadingState.postValue(NetworkState.LOADING)

        // COMPLETED: Make the second call here by setting the comicId for the key for next page
        api.getComicById(comicId).enqueue(object : Callback<Comic> {

            val comicList = mutableListOf<Comic>()

            override fun onResponse(call: Call<Comic>, response: Response<Comic>) {
                if (response.isSuccessful) {
                    val currentComic = response.body()

                    if (currentComic != null) {
                        comicId--

                        // COMPLETED: Return once the comic number reaches 1
                        if (comicId == 1) {
                            return
                        }

                        comicList.add(currentComic)
                        Log.d("XkcdDataSource", "LoadAfter: Current comic ID is $comicId")

                        callback.onResult(comicList, comicId)

                        updateState(NetworkState.LOADED)
                    }
                } else {
                    updateState(NetworkState.FAILED)
                }
            }

            override fun onFailure(call: Call<Comic>, t: Throwable) {
                Log.e("XkcdDataSource", "Failed to fetch data.")
                updateState(NetworkState.FAILED)
                //updateState(NetworkState.error("Error message ${t?.message}"))
            }
        })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Comic>
    ) {
        // No need to implement this method
    }

    private fun updateState(state: NetworkState) {
        this.netState.postValue(state)
    }
}