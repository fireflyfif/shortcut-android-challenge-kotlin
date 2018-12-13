package com.example.ivaivanova.myxkcd.ui.favfragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.ivaivanova.myxkcd.data.XkcdRepository
import com.example.ivaivanova.myxkcd.model.Comic

/**
 * ViewModel for the Favorite Comics
 */
class FavComicsViewModel(private val repository: XkcdRepository) : ViewModel() {

    var favComicsResult: LiveData<PagedList<Comic>>

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(10)
            .build()

        favComicsResult = LivePagedListBuilder(repository.getAllFavs(), config).build()
    }


    fun getFavComics(): LiveData<PagedList<Comic>> {
        return favComicsResult
    }

    // TODO: Move to the DetailViewModel
    fun insertInDb(favComic: Comic) {

        repository.insertItem(favComic)
    }

    // TODO: Move to the DetailViewModel
    fun deleteItemFromDb(comicNum: String) {
        repository.deleteItem(comicNum)
    }
}