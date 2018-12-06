package com.example.ivaivanova.myxkcd.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.ivaivanova.myxkcd.model.Comic

/**
 * Database schema that holds the list of repos.
 */
@Database(
    entities = [Comic::class],
    version = 1,
    exportSchema = false
)
abstract class ComicsDb : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: ComicsDb? = null

        // TODO: Do we still need to make this class Singleton
        fun getInstance(context: Context): ComicsDb =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: create(context)
                }

        // TODO: Why do we need the context here?
        private fun create(context: Context): ComicsDb {
            val databaseBuilder = Room.databaseBuilder(context, ComicsDb::class.java, XKCD_DB)
            return databaseBuilder.build()
        }

        const val XKCD_DB = "comics.db"

    }

    abstract fun comicsDao(): ComicsDao
}
