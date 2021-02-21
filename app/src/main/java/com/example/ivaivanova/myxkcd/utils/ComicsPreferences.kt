package com.example.ivaivanova.myxkcd.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class ComicsPreferences(context: Context) {

    companion object {
        private var INSTANCE: ComicsPreferences? = null
        private const val KEY_COMIC_NUMBER = "comic_number_key"


        /*fun getInstance(context: Context) : ComicsPreferences {
            INSTANCE ?: synchronized(this) {
                //preferences = PreferenceManager.getDefaultSharedPreferences(context)

            }
        }*/

    }

    private var preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)


    fun getComicNumber(): Int? {
        return  preferences.getInt(KEY_COMIC_NUMBER, -1)
    }

    fun setComicNumber(comicNumber: Int) {
        preferences.edit().putInt(KEY_COMIC_NUMBER, comicNumber).apply()
    }

}