package com.example.ivaivanova.myxkcd.ui.detailactivity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }companion object {

        private val INTENT_COMIC_PARCEL = "comic_parcel"

        fun newIntent(context: Context, comic: Comic?): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_COMIC_PARCEL, comic)
            return intent
        }
    }
}
