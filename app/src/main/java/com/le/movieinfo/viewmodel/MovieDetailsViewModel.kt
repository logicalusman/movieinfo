package com.le.movieinfo.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import com.le.movieinfo.domain.MovieCastData
import com.le.movieinfo.domain.MovieData
import com.le.movieinfo.model.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel(), LifecycleObserver {

    // public properties
    sealed class ViewState {
        class UpdateScreenTitle(val screenTitle: String) : ViewState()
        class ShowMovieCastList(val movieCastList: List<MovieCastData>) : ViewState()
    }

    var movieData: MovieData? = null
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    //private properties
    private val movieCastList: List<MovieCastData>? = null
    private val subscriptions = CompositeDisposable()

    //lifecycle methods
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        movieData?.title?.let {
            viewState.value = ViewState.UpdateScreenTitle(it)
        }
    }

    fun getMovieCast() {
        // If movie cast list was cached in ViewModel, send it and return.
        // It will save network calls when this ViewModel's view is re-created on orientation change
        if (updateMovieCastList(movieCastList)) {
            return
        }
        movieData?.let {
            subscriptions.add(
                    movieRepository.getMovieCast(it.id).subscribe {
                        if (it.success) {
                            it.data?.let {
                                if (it.isNotEmpty()) {
                                    viewState.value = ViewState.ShowMovieCastList(it)
                                }
                            }
                        }
                    }
            )
        }
    }

    private fun updateMovieCastList(mList: List<MovieCastData>?): Boolean {
        mList?.let {
            viewState.value = ViewState.ShowMovieCastList(it)
            return true
        }
        return false
    }

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

}