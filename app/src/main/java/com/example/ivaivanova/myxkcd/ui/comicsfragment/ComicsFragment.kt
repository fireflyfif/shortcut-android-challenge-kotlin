package com.example.ivaivanova.myxkcd.ui.comicsfragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import com.example.ivaivanova.myxkcd.ui.detailactivity.DetailComicIntent
import com.example.ivaivanova.myxkcd.utils.NetworkState


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ComicsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ComicsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ComicsFragment : Fragment() {

    // TODO: Fix issue with loading new data when tabs are changed
    // When I initialize the fragment from onCreate it loads new data on change orientation
    // If not - no default fragment is shown upon launching before selecting a bottom navigation tab

    private lateinit var comicsAdapter: XkcdAdapter
    private lateinit var comicsRv: RecyclerView
    private lateinit var swipeToRefresh: SwipeRefreshLayout

    // TODO: Question - To understand when to use lateinit variable and when not to
    private lateinit var viewModel: ComicsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("ComicsFragment", "onCreate called.")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("ComicsFragment", "onCreateView called.")

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_comics, container, false)

        // Initialize the recycler view first
        comicsRv = rootView.findViewById(R.id.comics_rv) as RecyclerView

        swipeToRefresh = rootView.findViewById(R.id.swipe_refresh) as SwipeRefreshLayout

        // Initialize the View Model
        viewModel = ViewModelProviders.of(this).get(ComicsViewModel::class.java)

        initAdapter()
        initSwipeToRefresh()

        return rootView
    }

    private fun initAdapter() {
        val columnCount: Int = resources.getInteger(R.integer.list_column_count)

        // TODO: Q- Do we still need to initialize the Adapter class if there is no functions in the arguments?
        // Initialize the Adapter
        comicsAdapter = XkcdAdapter(
            { viewModel.refreshData() },
            { comicItem: Comic? -> comicItemClicked(comicItem) })

        // The recycler view is null???
        comicsRv.layoutManager = StaggeredGridLayoutManager(
            columnCount,
            StaggeredGridLayoutManager.VERTICAL
        )
        // Set the adapter to the recycler view
        comicsRv.adapter = comicsAdapter

        viewModel.comicsResult.observe(this, Observer {
            comicsAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            comicsAdapter.setNetworkState(it!!)
        })
    }


    // TODO: Check why is this method not working?
    private fun initSwipeToRefresh() {

        viewModel.comicsResult.observe(this, Observer {
            comicsAdapter.submitList(it)
        })

        swipeToRefresh.setOnRefreshListener {
            viewModel.refreshData()

            // Hide the refresh icon
            swipeToRefresh.isRefreshing = false
        }

        initAdapter()
    }

    /**
     * Method that handles the click on an item
     * source: https://medium.com/@passsy/starting-activities-with-kotlin-my-journey-8b7307f1e460
     */
    private fun comicItemClicked(currentComic: Comic?) {

        startActivity(activity?.DetailComicIntent(currentComic))
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ComicsFragment.
         */
        fun newInstance(): ComicsFragment =
            ComicsFragment()
    }
}
