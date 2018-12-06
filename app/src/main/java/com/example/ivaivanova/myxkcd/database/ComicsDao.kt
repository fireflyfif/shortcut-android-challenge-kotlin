package com.example.ivaivanova.myxkcd.database

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.ivaivanova.myxkcd.model.Comic


@Dao
interface ComicsDao {

    @Insert
    fun insertComic(comics: List<Comic>)

    @Query("SELECT * FROM Comic")
    fun getAllComics(): DataSource.Factory<Int, Comic>
}