package com.le.movieinfo.view.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.le.movieinfo.MovieInfoApp
import com.le.movieinfo.R
import com.le.movieinfo.domain.MovieData
import com.le.movieinfo.view.activity.MovieDetailsActivity
import com.le.movieinfo.view.adapter.MovieListAdapter
import com.le.movieinfo.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


/**
 * Displays home screen, containing movies list and buttons to filter list.
 *
 */
class MovieListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieListViewModel
    private val TAG = "MovieListFragment"
    private var movieListAdapter: MovieListAdapter? = null

    init {
        MovieInfoApp.dataComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MovieListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)
        initViews()
        subscribeViewStateChanges()
        viewModel.getUpcomingMovies()

    }

    private fun initViews() {
        setupRecyclerView()
        filter_latest_btn.setOnClickListener {
            viewModel.filterMovieListShowLatestFirst()
        }
        filter_oldest_btn.setOnClickListener({
            viewModel.filterMovieListShowOldestFirst()
        })
        movieListAdapter?.rowClickSubject?.subscribe {
            val i = Intent(activity, MovieDetailsActivity::class.java)
            i.putExtra(MovieDetailsActivity.EXTRA_MOVIE_DATA, it)
            startActivity(i)
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        movie_list_rv.layoutManager = layoutManager
        movie_list_rv.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
        movieListAdapter = MovieListAdapter(activity!!.applicationContext)
        movie_list_rv.adapter = movieListAdapter
    }

    private fun subscribeViewStateChanges() {
        viewModel.viewState.observe(this, Observer {
            updateViewState(it)
        })
    }

    private fun updateViewState(viewState: MovieListViewModel.ViewState?) {
        when (viewState) {
            is MovieListViewModel.ViewState.UpdateScreenTitle -> activity?.setTitle(viewState.screenTitle)
            is MovieListViewModel.ViewState.ShowLoading -> showLoading(viewState.show)
            is MovieListViewModel.ViewState.ShowErrorMessage -> showErrorMessage(viewState.message)
            is MovieListViewModel.ViewState.ShowMovieList -> showMovieList(viewState.upcomingMovies)
            is MovieListViewModel.ViewState.ShowFilterButtons -> showFilterButtons(viewState.show)
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }


    private fun showMovieList(movieList: List<MovieData>) {
        Log.d(TAG, "Received movie list:" + movieList.size)
        movieListAdapter?.moviesList = movieList
    }

    private fun showFilterButtons(show: Boolean) {
        if (show) {
            filter_latest_btn.visibility = View.VISIBLE
            filter_oldest_btn.visibility = View.VISIBLE
        } else {
            filter_latest_btn.visibility = View.GONE
            filter_oldest_btn.visibility = View.GONE
        }
    }

    private fun showErrorMessage(@StringRes message: Int) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance(): Fragment {
            return MovieListFragment()
        }
    }

}
