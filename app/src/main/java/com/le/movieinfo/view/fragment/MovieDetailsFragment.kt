package com.le.movieinfo.view.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.le.movieinfo.MovieInfoApp
import com.le.movieinfo.R
import com.le.movieinfo.domain.MovieCastData
import com.le.movieinfo.domain.MovieData
import com.le.movieinfo.view.adapter.MovieCastListAdapter
import com.le.movieinfo.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject

/**
 * Shows movie details.
 *
 */
class MovieDetailsFragment : Fragment() {

    private var movieData: MovieData? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieDetailsViewModel
    private var movieCastListAdapter: MovieCastListAdapter? = null

    init {
        MovieInfoApp.dataComponent.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MovieDetailsViewModel::class.java)
        if (movieData != null) {
            viewModel.movieData = movieData
        } else {
            movieData = viewModel.movieData
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)
        initViews()
        setupRecyclerView()
        subscribeViewStateChanges()
        viewModel.getMovieCast()
    }

    private fun initViews() {
        setupRecyclerView()
        Glide.with(activity as Context).load("${viewModel.movieData?.movieLargePosterUrl}${viewModel.movieData?.posterImagePath}").into(movie_poster_iv)
        movie_overview_tv.text = viewModel.movieData?.overview
        movie_release_date_tv.text = viewModel.movieData?.releaseDate
        movie_rating_value_tv.text = "${viewModel.movieData?.voteAverage}"
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        movie_cast_list_rv.layoutManager = layoutManager
        ViewCompat.setNestedScrollingEnabled(movie_cast_list_rv, false)
        movieCastListAdapter = MovieCastListAdapter(activity!!.applicationContext)
        movie_cast_list_rv.adapter = movieCastListAdapter
    }

    private fun subscribeViewStateChanges() {
        viewModel.viewState.observe(this, Observer {
            updateViewState(it)
        })
    }

    private fun updateViewState(viewState: MovieDetailsViewModel.ViewState?) {
        when (viewState) {
            is MovieDetailsViewModel.ViewState.UpdateScreenTitle -> activity?.title = viewState.screenTitle
            is MovieDetailsViewModel.ViewState.ShowMovieCastList -> showMovieCastList(viewState.movieCastList)
        }
    }

    private fun showMovieCastList(movieCastList: List<MovieCastData>) {
        movieCastListAdapter?.movieCastList = movieCastList
    }

    companion object {
        fun newInstance(movieData: MovieData?): Fragment {
            val fragment = MovieDetailsFragment()
            fragment.movieData = movieData
            return fragment
        }
    }
}
