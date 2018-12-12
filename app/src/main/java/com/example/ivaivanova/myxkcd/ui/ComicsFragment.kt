package com.example.ivaivanova.myxkcd.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
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
import com.example.ivaivanova.myxkcd.ui.detailactivity.DetailActivity
import com.example.ivaivanova.myxkcd.ui.detailactivity.DetailComicIntent
import kotlinx.android.synthetic.main.fragment_comics.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null

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

        // TODO: Question - Why the Kotlin extensions are not working within a Fragment?
        // Initialize the recycler view first
        comicsRv = rootView.findViewById(R.id.comics_rv) as RecyclerView

        swipeToRefresh = rootView.findViewById(R.id.swipe_refresh) as SwipeRefreshLayout

        // Initialize the View Model
        viewModel = ViewModelProviders.of(this).get(ComicsViewModel::class.java)

        initAdapter()
        initializeViewModel()
        initSwipeToRefresh()

        return rootView
    }

    private fun initAdapter() {
        val columnCount: Int = resources.getInteger(R.integer.list_column_count)

        comicsAdapter = XkcdAdapter(
            { viewModel.refreshData() },
            { comicItem: Comic? -> comicItemClicked(comicItem) })

        // The recycler view is null???
        comicsRv.layoutManager = StaggeredGridLayoutManager(
            columnCount,
            StaggeredGridLayoutManager.VERTICAL
        )
        comicsRv.adapter = comicsAdapter
    }


    private fun initializeViewModel() {
        viewModel.comicsResult.observe(this, Observer {
            comicsAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            comicsAdapter.setNetworkState(it!!)
        })
    }

    // TODO: Check why is this method not working?
    private fun initSwipeToRefresh() {

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


    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
//        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    /*interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ComicsFragment.
         */
        fun newInstance(): ComicsFragment = ComicsFragment()
    }
}
