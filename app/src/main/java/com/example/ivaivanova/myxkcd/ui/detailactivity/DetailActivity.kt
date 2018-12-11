package com.example.ivaivanova.myxkcd.ui.detailactivity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

fun Context.DetailComicIntent(currentComic: Comic?): Intent {
    return Intent(this, DetailActivity::class.java).apply {
        putExtra(INTENT_COMIC_PARCEL, currentComic)
    }
}

private const val INTENT_COMIC_PARCEL = "comic_parcel"

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val currentComic = intent.getParcelableExtra<Comic>(INTENT_COMIC_PARCEL)
        setupUi(currentComic)
    }

    private fun setupUi(currentComic: Comic) {
        //var comicTitle = collapsing_toolbar.title.isNullOrEmpty()
        collapsing_toolbar.setExpandedTitleColor(resources.getColor(R.color.colorPrimary))
        collapsing_toolbar.title = currentComic.title
        comic_detail_month.text = currentComic.month
        comic_detail_year.text = currentComic.year
        comic_detail_alt.text = currentComic.alt
        comic_detail_number.text = currentComic.num.toString()
        comic_detail_description.text = currentComic.transcript

        Picasso.get().load(currentComic.image).into(comic_detail_image)
    }
}
