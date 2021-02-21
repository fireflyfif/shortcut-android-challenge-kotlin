package com.example.ivaivanova.myxkcd.ui.comicsfragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comic_item.view.*

/**
 * ViewHolder for the Comics RecyclerView list item
 */
class ComicsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val comicImage: ImageView = view.findViewById(R.id.comic_image)
    private var comic: Comic? = null


    fun bind(comics: Comic?, listener: (Comic?) -> Unit) = with(itemView) {
        comic = comics

        if (comics != null) {
            // Set the details on the current comic
            comic_title.text = comics.title
            comic_month.text = comics.month
            comic_number.text = comics.num.toString()
            comic_year.text = comics.year

            // Set the image with Picasso
            Picasso.get().load(comics.image).into(comicImage)
        }

        setOnClickListener { listener(comics) }
    }

    companion object {
        fun create(parent: ViewGroup): ComicsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.comic_item, parent, false)
            return ComicsViewHolder(view)
        }
    }
}