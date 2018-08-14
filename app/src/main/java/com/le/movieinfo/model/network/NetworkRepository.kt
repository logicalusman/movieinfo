package com.le.movieinfo.model.network

import com.le.movieinfo.domain.MovieCastData
import com.le.movieinfo.domain.MovieData
import com.le.movieinfo.domain.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject

class NetworkRepository @Inject constructor(var theMovieDbApi: TheMovieDbApi) {


    fun getUpcomingMovies(): Observable<Result<List<MovieData>>> {
        val moviesListSubject = SingleSubject.create<Result<List<MovieData>>>()
        theMovieDbApi.getUpcomingMovies(TheMovieDbApi.API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    moviesListSubject.onSuccess(Result.fomData(Mapper.toMovieData(it.moviesList)))
                },
                {
                    moviesListSubject.onSuccess(Result.fromError(it))
                })

        return moviesListSubject.toObservable()
    }

    fun getMovieCast(movieId : Int) : Observable<Result<List<MovieCastData>>>{
        val movieCastSubject = SingleSubject.create<Result<List<MovieCastData>>>()
        theMovieDbApi.getMovieCast(movieId,TheMovieDbApi.API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    movieCastSubject.onSuccess(Result.fomData(Mapper.toMovieCastData(it.castList)))
                },
                {
                    movieCastSubject.onSuccess(Result.fromError(it))
                })

        return movieCastSubject.toObservable()
    }


}