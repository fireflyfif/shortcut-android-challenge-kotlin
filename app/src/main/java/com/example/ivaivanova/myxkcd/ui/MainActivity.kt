package com.example.ivaivanova.myxkcd.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ivaivanova.myxkcd.R

class MainActivity : AppCompatActivity() {

    private val adapter = XkcdAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun initializeAdapter() {


    }
}
