package com.example.ivaivanova.myxkcd

import android.app.Application
import android.content.Context
import com.example.ivaivanova.myxkcd.database.ComicsDb
import com.example.ivaivanova.myxkcd.utils.ComicsPreferences

class App : Application() {

    //var context: Context? = null

    companion object {
        var prefs: ComicsPreferences? = null
        // TODO: The lint is saying this could produce a memory leak. What should I do?
        // source: https://stackoverflow.com/a/5114361/8132331
        // Dagger graph
        lateinit var context: Context
    }

    override fun onCreate() {
        prefs = ComicsPreferences(applicationContext)
        context = applicationContext
        ComicsDb.create(context)
        super.onCreate()
    }

    fun getContext() : Context {
        return context
    }
}