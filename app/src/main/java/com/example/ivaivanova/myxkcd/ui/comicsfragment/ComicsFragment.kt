package com.example.ivaivanova.myxkcd.ui.comicsfragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.format.Time
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.work.*
import com.example.ivaivanova.myxkcd.R
import com.example.ivaivanova.myxkcd.model.Comic
import com.example.ivaivanova.myxkcd.ui.detailactivity.DetailComicIntent
import com.example.ivaivanova.myxkcd.utils.NetworkState
import com.example.ivaivanova.myxkcd.utils.NewComicBgWork
import java.util.*
import java.util.Calendar.*
import java.util.concurrent.TimeUnit


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
    private lateinit var errorMsg: TextView
    private lateinit var progressBar: ProgressBar

    // COMPLETED: Question - To understand when to use lateinit variable and when not to
    private lateinit var viewModel: ComicsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataMessage: Data = Data.Builder()
            .putString(NewComicBgWork.TASK_DESC, "Tab to check it out")
            .build()

        // This is used only for a single scheduled work
        val workRequest: OneTimeWorkRequest = OneTimeWorkRequest.Builder(NewComicBgWork::class.java)
            .setInputData(dataMessage)
            .build()

        // COMPLETED: Add constraints on the working request so that it is done only when the device is idle and charged
        val constraints: Constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        // TODO: Make the period work to be scheduled every Monday, Wednesday and Friday
        // So far it's not possible to achieve exact times using PeriodicWorkRequest.
        // An ugly work-around would be using a OneTimeWorkRequest and when it fires,
        // set another OneTimeWorkRequest with a new calculated period, and so on.
        // source: https://stackoverflow.com/a/51904623/8132331
        /*val periodicWorkRequest = PeriodicWorkRequest.Builder(NewComicBgWork::class.java, 12, TimeUnit.HOURS)
            .setInputData(dataMessage)
            .setConstraints(constraints)
            .build()*/

        //WorkManager.getInstance().enqueue(periodicWorkRequest)

        // TODO: Not sure what to observe here?
        /*WorkManager.getInstance().getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(this, Observer {
                    WorkInfo ->

            })*/

        Log.d("ComicsFragment", "onCreate called.")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("ComicsFragment", "onCreateView called.")

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_comics, container, false)

        // Initialize the views first
        with(rootView) {
            comicsRv = findViewById(R.id.comics_rv)
            swipeToRefresh = findViewById(R.id.swipe_refresh)
            errorMsg = findViewById(R.id.error_message)
            progressBar = findViewById(R.id.progress_bar)
        }

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
            { refreshComicsOnButtonClicked() },
            { comicItem: Comic? -> comicItemClicked(comicItem) })

        // The recycler view is null???
        comicsRv.layoutManager = StaggeredGridLayoutManager(
            columnCount,
            StaggeredGridLayoutManager.VERTICAL
        )
        // Set the adapter to the recycler view
        comicsRv.adapter = comicsAdapter

        initViewModel()

        viewModel.getState().observe(this, Observer { state ->
            progressBar.visibility = if (viewModel.listIsEmpty() && state == NetworkState.LOADING)
                View.VISIBLE else View.GONE

            errorMsg.visibility = if (viewModel.listIsEmpty() && state == NetworkState.FAILED)
                View.VISIBLE else View.GONE

            comicsAdapter.setNetworkState(state ?: NetworkState.LOADED)
        })
    }

    private fun initViewModel() {
        viewModel.comicsResult.observe(this, Observer {
            comicsAdapter.submitList(it)
        })
    }

    private fun initSwipeToRefresh() {

        // TODO: Question: Why do I have to initialize the ViewModel with the data here?
        // Why is the refreshData() not enough
        initViewModel()

        swipeToRefresh.setOnRefreshListener {
            viewModel.refreshData()

            initAdapter()
            // Hide the refresh icon
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun refreshComicsOnButtonClicked() {
        initViewModel()
        viewModel.refreshData()
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

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        return super.onContextItemSelected(item)
    }
}
