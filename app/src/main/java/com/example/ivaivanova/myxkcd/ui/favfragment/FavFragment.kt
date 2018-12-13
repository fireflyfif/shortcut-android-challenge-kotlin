package com.example.ivaivanova.myxkcd.ui.favfragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.utils.Injection
import kotlinx.android.synthetic.main.fragment_fav.*
import kotlinx.android.synthetic.main.fragment_fav.view.*

class FavFragment : Fragment() {

    private lateinit var viewModel: FavComicsViewModel
    private lateinit var favAdapter: FavComicsAdapter

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ComicsFragment.
         */
        fun newInstance(): FavFragment =
            FavFragment()
    }

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
        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(rootView.context))
            .get(FavComicsViewModel::class.java)

        // Initialize the Adapter
        favAdapter = FavComicsAdapter()

        // Set the LinearLayout
        rootView.fav_comics_rv.layoutManager = LinearLayoutManager(rootView.context,
            LinearLayoutManager.VERTICAL, false)
        // Set the Adapter on the recycler view
        rootView.fav_comics_rv.adapter = favAdapter

        initViewModel()

        return rootView
    }

    private fun initViewModel() {
        viewModel.getFavComics().observe(this, Observer {

            if (it != null && it.size > 0) {
                fav_progress_bar.visibility = View.INVISIBLE
                fav_empty_text.visibility = View.INVISIBLE
                favAdapter.submitList(it)
            } else {
                fav_progress_bar.visibility = View.INVISIBLE
                fav_empty_text.visibility = View.VISIBLE
            }


        })
    }

}