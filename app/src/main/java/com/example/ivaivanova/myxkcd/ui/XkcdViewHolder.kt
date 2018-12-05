package com.example.ivaivanova.myxkcd.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comic_item.view.*

/**
 * ViewHolder for the Comics RecyclerView list item
 */
class XkcdViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val comicTitle: TextView = view.findViewById(R.id.comic_title)
    private val comicMonth: TextView = view.findViewById(R.id.comic_month)
    private val comicNumber: TextView = view.findViewById(R.id.comic_number)
    private val comicYear: TextView = view.findViewById(R.id.comic_year)
    private val comicImage: ImageView = view.findViewById(R.id.comic_image)


    fun bind(comics: Comic?) {
        if (comics != null) {
            // Set the details on the current comic
            comicTitle.text = comics.title
            comicMonth.text = comics.month
            comicNumber.text = comics.num.toString()
            comicYear.text = comics.year

            // Set the image with Picasso
            Picasso.get().load(comics.image).into(comicImage)
        }
    }

    companion object {
        fun create(parent: ViewGroup) : XkcdViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.comic_item, parent, false)
            return XkcdViewHolder(view)
        }
    }
}