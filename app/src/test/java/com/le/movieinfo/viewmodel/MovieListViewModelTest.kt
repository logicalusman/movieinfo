package com.le.movieinfo.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import com.le.movieinfo.R
import com.le.movieinfo.domain.MovieData
import com.le.movieinfo.domain.Result
import com.le.movieinfo.model.MovieRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.mock

class MovieListViewModelTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    val movie1 = MovieData(1, "Mission Impossible", "Lorem Ipsum Lorem Ipsum", 100, 1000.00, "/imgPath", "/imgBackdropPath", 8.5, "2018-07-25", "en")
    val movie2 = MovieData(2, "Fight Club", "Lorem Ipsum Lorem Ipsum", 100, 1000.00, "/imgPath", "/imgBackdropPath", 8.5, "2017-08-25", "en")
    val movie3 = MovieData(3, "Dead poets society", "Lorem Ipsum Lorem Ipsum", 100, 1000.00, "/imgPath", "/imgBackdropPath", 8.5, "2018-01-15", "en")
    lateinit var movieList: ArrayList<MovieData>
    lateinit var movieRepositoryMock: MovieRepository
    lateinit var movieListViewModel: MovieListViewModel
    // mockObserver will mock the view observing the ViewState
    var mockObserver: Observer<MovieListViewModel.ViewState> = mock()

    @Before
    fun setUp() {
        movieList = ArrayList()
        movieList.add(movie1)
        movieList.add(movie2)
        movieList.add(movie3)
        val result = Result.fomData<List<MovieData>>(movieList)
        movieRepositoryMock = mock()
        {
            on { getUpcomingMovies() } doReturn Observable.just<Result<List<MovieData>>>(result)
        }
        movieListViewModel = MovieListViewModel(movieRepositoryMock)
    }

    @Test
    fun onCreated() {
        movieListViewModel.viewState.observeForever(mockObserver)
        // When view goes onCreate state, the viewmodel sets the title to the view.
        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.addObserver(movieListViewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        // verify if livedata observer has got the right state
        Mockito.verify(mockObserver).onChanged(movieListViewModel.viewState.value as MovieListViewModel.ViewState.UpdateScreenTitle)
        // verify if UpdateScreenTitle state has the right screen title
        Assert.assertEquals(R.string.app_name, (movieListViewModel.viewState.value as MovieListViewModel.ViewState.UpdateScreenTitle).screenTitle)
        movieListViewModel.viewState.removeObserver(mockObserver)
    }

    @Test
    fun getUpcomingMovies() {
        // capture states
        val states = mutableListOf<MovieListViewModel.ViewState>()
        mockObserver = Observer {
            it?.let {
                states.add(it)
            }
        }
        movieListViewModel.viewState.observeForever(mockObserver)
        movieListViewModel.getUpcomingMovies()

        // verify all state changes after calling getUpcomingMovies

        Assert.assertTrue(states.get(0) is MovieListViewModel.ViewState.ShowLoading)
        Assert.assertEquals(true, (states.get(0) as MovieListViewModel.ViewState.ShowLoading).show)

        Assert.assertTrue(states.get(1) is MovieListViewModel.ViewState.ShowLoading)
        Assert.assertEquals(false, (states.get(1) as MovieListViewModel.ViewState.ShowLoading).show)

        Assert.assertTrue(states.get(2) is MovieListViewModel.ViewState.ShowMovieList)
        Assert.assertEquals(movieList.size, (states.get(2) as MovieListViewModel.ViewState.ShowMovieList).upcomingMovies.size)

        Assert.assertTrue(states.get(3) is MovieListViewModel.ViewState.ShowFilterButtons)
        Assert.assertEquals(true, (states.get(3) as MovieListViewModel.ViewState.ShowFilterButtons).show)

        movieListViewModel.viewState.removeObserver(mockObserver)
    }

}