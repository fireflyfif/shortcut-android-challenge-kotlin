package com.example.ivaivanova.myxkcd.ui.detailactivity

import android.R.attr.uiOptions
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import com.example.ivaivanova.myxkcd.utils.Injection
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Method that handles the click on an item
 * source: https://medium.com/@passsy/starting-activities-with-kotlin-my-journey-8b7307f1e460
 */
fun Context.DetailComicIntent(currentComic: Comic?): Intent {
    return Intent(this, DetailActivity::class.java).apply {
        putExtra(INTENT_COMIC_PARCEL, currentComic)
    }
}

private const val INTENT_COMIC_PARCEL = "comic_parcel"

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailComicViewModel
    private lateinit var comicNumber: String
    private var currentComic: Comic? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        currentComic = intent.getParcelableExtra(INTENT_COMIC_PARCEL)
        setupUi(currentComic)

        // Initialize the ViewModel
        viewModel = ViewModelProviders.of(this, Injection.provideDetailViewModelFactory(
            this.applicationContext)).get(DetailComicViewModel::class.java)

    }

    private fun addComicToFavs(comic: Comic?) {
        // TODO: Q- How to get the value of comics for the scope of this method?
        viewModel.insertInDb(comic)
    }

    private fun deleteComic(comicNum: String) {
        viewModel.deleteItemFromDb(comicNum)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun setupUi(currentComic: Comic?) {
        // Set the title of the collapsing toolbar to the title of the current comic
        comicNumber = currentComic?.num.toString()
        collapsing_toolbar.setExpandedTitleColor(resources.getColor(R.color.colorWhite))
        collapsing_toolbar.title = currentComic?.title
        comic_detail_month.text = currentComic?.month
        comic_detail_year.text = currentComic?.year
        comic_detail_alt.text = currentComic?.alt
        comic_detail_number.text = comicNumber
        comic_detail_description.text = currentComic?.transcript

        Picasso.get().load(currentComic?.image).into(comic_detail_image)

        val params = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        comic_detail_image.setOnPhotoTapListener { view, x, y ->
            comic_detail_image.layoutParams = params
            comic_detail_image.adjustViewBounds = true
            fullScreen()
            Snackbar.make(coordinator_detail_comic, "Image is clicked", Snackbar.LENGTH_SHORT).show()
        }

        //fullScreen()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    // Source: https://github.com/chrisbanes/PhotoView/blob/master/sample/src/main/java/com/github/chrisbanes/photoview/sample/ImmersiveActivity.java
    private fun fullScreen() {
        val uiOptions = window.decorView.systemUiVisibility
        val newUiOptions = uiOptions
        //var isImmersiveModeEnabled: Boolean = isImmersiveModeEnabled()

        /*if (isImmersiveModeEnabled) {
            Log.i("TEST", "Turning immersive mode mode off. ");
        } else {
            Log.i("TEST", "Turning immersive mode mode on.");
        }*/

        // Navigation bar hiding
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }

        // Status bar hiding
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        // Immersive mode: Backward compatible to KitKat.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }

        window.decorView.systemUiVisibility = newUiOptions
    }

    // TODO: Understand this function and refine it
    /*@RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun isImmersiveModeEnabled(): Boolean {
        return uiOptions || View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_comic_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_delete ->  {
                deleteComic(comicNumber)
                Snackbar.make(coordinator_detail_comic, "Comic deleted!", Snackbar.LENGTH_SHORT)
                    .show()
                return true
            }

            R.id.action_insert -> {
                addComicToFavs(currentComic)
                Snackbar.make(coordinator_detail_comic, "Comic starred!", Snackbar.LENGTH_SHORT)
                    .show()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
