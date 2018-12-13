package com.example.ivaivanova.myxkcd.database

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.ivaivanova.myxkcd.model.Comic

/**
 * Data Access Objects with CRUD methods
 */
@Dao
interface ComicsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComic(favComic: Comic?)

    @Query("SELECT * FROM comics")
    fun getAllComics(): DataSource.Factory<Int, Comic>

    @Query("DELETE FROM comics WHERE num = :comicNumber")
    fun deleteComic(comicNumber: String)
}