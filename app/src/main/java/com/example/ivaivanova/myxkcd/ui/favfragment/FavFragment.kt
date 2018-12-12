package com.example.ivaivanova.myxkcd.ui.favfragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.utils.Injection

class FavFragment : Fragment() {

    private lateinit var viewModel: FavComicsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_fav, container, false)

        // Initialize the ViewModel
        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(activity!!.applicationContext))
            .get(FavComicsViewModel::class.java)

        val fabButton = rootView.findViewById<FloatingActionButton>(R.id.fav_button)
        fabButton.setOnClickListener {

        }

        return rootView
    }

    private fun initViewModel() {
        viewModel.getFavComics().observe(this, Observer {

        })
    }

    private fun addComicToFavs() {

        viewModel.insertInDb()
    }
}