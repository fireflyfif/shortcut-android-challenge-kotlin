package com.example.ivaivanova.myxkcd.ui.favfragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import com.squareup.picasso.Picasso

/**
 * ViewHolder for the Comics RecyclerView list item
 */
class FavComicsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val comicTitle: TextView = view.findViewById(R.id.fav_comic_title)
    private val comicMonth: TextView = view.findViewById(R.id.fav_comic_month)
    private val comicNumber: TextView = view.findViewById(R.id.fav_comic_number)
    private val comicYear: TextView = view.findViewById(R.id.fav_comic_year)
    private val comicImage: ImageView = view.findViewById(R.id.fav_comic_image)
    private val comicDetails: TextView = view.findViewById(R.id.fav_comic_alt)
    private var comic: Comic? = null


    fun bind(comics: Comic?) {
        comic = comics

        if (comics != null) {
            // Set the details on the current comic
            comicTitle.text = comics.title
            comicMonth.text = comics.month
            comicNumber.text = comics.num.toString()
            comicYear.text = comics.year
            comicDetails.text = comics.alt

            // Set the image with Picasso
            Picasso.get().load(comics.image).into(comicImage)
        }

        //setOnClickListener { listener(comics) }
    }

    companion object {
        fun create(parent: ViewGroup): FavComicsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fav_comic_item, parent, false)
            return FavComicsViewHolder(view)
        }
    }
}