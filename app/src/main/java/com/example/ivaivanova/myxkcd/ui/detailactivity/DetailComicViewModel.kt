package com.example.ivaivanova.myxkcd.ui.detailactivity

import android.arch.lifecycle.ViewModel
import com.example.ivaivanova.myxkcd.data.XkcdRepository
import com.example.ivaivanova.myxkcd.model.Comic

class DetailComicViewModel(private val repository: XkcdRepository) : ViewModel() {


    fun insertInDb(favComic: Comic?) {

        repository.insertItem(favComic)
    }

    fun deleteItemFromDb(comicNum: String) {
        repository.deleteItem(comicNum)
    }
}