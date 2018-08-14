package com.le.movieinfo.model

import com.le.movieinfo.domain.MovieCastData
import com.le.movieinfo.domain.MovieData
import com.le.movieinfo.domain.Result
import com.le.movieinfo.model.network.NetworkRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * <p>
 * Facade for data operations, a.k.a repository pattern. This class is a central point for
 * viewmodels to perform data operations without bothering if an operation is performed on network or
 * to a local db or, even, both. It is also a perfect place for caching network data.
 * </p>
 * https://developer.android.com/jetpack/docs/guide#connecting_viewmodel_and_the_repository
 *
 */
class MovieRepository @Inject constructor(private var networkRepository: NetworkRepository) {

    fun getUpcomingMovies(): Observable<Result<List<MovieData>>> {
        return networkRepository.getUpcomingMovies()
    }

    fun getMovieCast(movieId: Int): Observable<Result<List<MovieCastData>>> {
        return networkRepository.getMovieCast(movieId)
    }
}