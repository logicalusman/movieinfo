package com.le.movieinfo.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import com.le.movieinfo.domain.MovieCastData
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

class MovieDetailsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    val movieData = MovieData(1, "Mission Impossible", "Lorem Ipsum Lorem Ipsum", 100, 1000.00, "/imgPath", "/imgBackdropPath", 8.5, "2018-07-25", "en")
    val cast1 = MovieCastData("Bruce Lee", "/profilePicturePath")
    val cast2 = MovieCastData("Tom Cruise", "/profilePicturePath")
    lateinit var movieCastList: ArrayList<MovieCastData>
    lateinit var movieRepositoryMock: MovieRepository
    lateinit var movieDetailsViewModel: MovieDetailsViewModel
    // mockObserver will mock the view observing the ViewState
    var mockObserver: Observer<MovieDetailsViewModel.ViewState> = mock()

    @Before
    fun setUp() {
        movieCastList = ArrayList()
        movieCastList.add(cast1)
        movieCastList.add(cast2)
        val result = Result.fomData<List<MovieCastData>>(movieCastList)
        movieRepositoryMock = mock()
        {
            on { getMovieCast(1) } doReturn Observable.just<Result<List<MovieCastData>>>(result)
        }
        movieDetailsViewModel = MovieDetailsViewModel(movieRepositoryMock)
        movieDetailsViewModel.movieData = movieData
    }

    @Test
    fun onCreated() {
        movieDetailsViewModel.viewState.observeForever(mockObserver)
        // When view goes onCreate state, the viewmodel sets the title to the view.
        val lifecycle = LifecycleRegistry(Mockito.mock(LifecycleOwner::class.java))
        lifecycle.addObserver(movieDetailsViewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        // verify if livedata observer has got the right state
        Mockito.verify(mockObserver).onChanged(movieDetailsViewModel.viewState.value as MovieDetailsViewModel.ViewState.UpdateScreenTitle)
        // verify if UpdateScreenTitle state has the right screen title
        Assert.assertEquals(movieData.title, (movieDetailsViewModel.viewState.value as MovieDetailsViewModel.ViewState.UpdateScreenTitle).screenTitle)
        movieDetailsViewModel.viewState.removeObserver(mockObserver)
    }

    @Test
    fun getMovieCast() {
        // capture states
        val states = mutableListOf<MovieDetailsViewModel.ViewState>()
        mockObserver = Observer {
            it?.let {
                states.add(it)
            }
        }
        movieDetailsViewModel.viewState.observeForever(mockObserver)
        movieDetailsViewModel.getMovieCast()

        // verify state changes after calling getUpcomingMovies
        Assert.assertTrue(states.get(0) is MovieDetailsViewModel.ViewState.ShowMovieCastList)
        Assert.assertEquals(movieCastList.size, (states.get(0) as MovieDetailsViewModel.ViewState.ShowMovieCastList).movieCastList.size)

        movieDetailsViewModel.viewState.removeObserver(mockObserver)
    }
}