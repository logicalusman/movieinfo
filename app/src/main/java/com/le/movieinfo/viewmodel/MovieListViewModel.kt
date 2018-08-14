package com.le.movieinfo.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.support.annotation.StringRes
import com.le.movieinfo.R
import com.le.movieinfo.domain.ErrorType
import com.le.movieinfo.domain.MovieData
import com.le.movieinfo.model.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieListViewModel @Inject constructor(var movieRepository: MovieRepository) : ViewModel(), LifecycleObserver {

    // public properties
    sealed class ViewState {
        class ShowLoading(val show: Boolean) : ViewState()
        class ShowErrorMessage(@StringRes val message: Int) : ViewState()
        class UpdateScreenTitle(@StringRes val screenTitle: Int) : ViewState()
        class ShowMovieList(val upcomingMovies: List<MovieData>) : ViewState()
        class ShowFilterButtons(val show: Boolean) : ViewState()
    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    //private properties
    private val subscriptions = CompositeDisposable()
    private var movieList: List<MovieData>? = null

    //lifecycle methods
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        viewState.value = ViewState.UpdateScreenTitle(R.string.app_name)
    }

    //public methods
    fun getUpcomingMovies() {
        // If movie list was cached in ViewModel, send it and return.
        // It will save network calls when this ViewModel's view is re-created on orientation change
        if (updateMovieList(movieList)) {
            viewState.postValue(ViewState.ShowFilterButtons(true))
            return
        }
        viewState.value = ViewState.ShowLoading(true)
        subscriptions.add(
                movieRepository.getUpcomingMovies().subscribe {
                    viewState.value = ViewState.ShowLoading(false)
                    if (it.success) {
                        it.data?.let {
                            movieList = it
                            updateMovieList(it)
                            if (it.isNotEmpty()) {
                                viewState.value = ViewState.ShowFilterButtons(true)
                            }
                        }
                    } else {
                        when (it.errorType) {
                            is ErrorType.NetworkError -> viewState.value = ViewState.ShowErrorMessage(R.string.network_error)
                            is ErrorType.TimeoutError -> viewState.value = ViewState.ShowErrorMessage(R.string.connection_timeout)
                            else -> viewState.value = ViewState.ShowErrorMessage(R.string.unknown_error)
                        }
                    }
                }
        )
    }

    fun filterMovieListShowLatestFirst() {
        filterMovieList(true)
    }

    fun filterMovieListShowOldestFirst() {
        filterMovieList(false)
    }

    // private methods
    /**
     * Filters movie list release date wise.
     * @param latestFirst true will show the latest releases first, false will show oldest ones
     */
    private fun filterMovieList(latestFirst: Boolean) {
        movieList?.let {
            if (it.isNotEmpty()) {
                if (latestFirst) {
                    movieList = it.sortedWith(Comparator { movieData1, movieData2 ->
                        movieData2.releaseDate().compareTo(movieData1.releaseDate())
                    })
                } else {
                    movieList = it.sortedWith(Comparator { movieData1, movieData2 ->
                        movieData1.releaseDate().compareTo(movieData2.releaseDate())

                    })
                }
                updateMovieList(movieList)
            }
        }
    }

    private fun updateMovieList(mList: List<MovieData>?): Boolean {
        mList?.let {
            viewState.value = ViewState.ShowMovieList(it)
            return true
        }
        return false
    }

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

}