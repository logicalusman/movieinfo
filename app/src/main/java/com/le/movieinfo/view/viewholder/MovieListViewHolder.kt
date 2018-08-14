package com.le.movieinfo.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.le.movieinfo.domain.MovieData
import kotlinx.android.synthetic.main.movie_list_cell.view.*
import rx.subjects.PublishSubject

class MovieListViewHolder(val view: View, rowClickSubject: PublishSubject<MovieData>) : RecyclerView.ViewHolder(view) {

    private lateinit var movieData: MovieData
    private val moviePoster = view.movie_poster_iv
    private val movieTitle = view.movie_title_tv
    private val movieReleaseDate = view.movie_release_date_tv

    init {
        view.setOnClickListener({
            rowClickSubject.onNext(movieData)
        })
    }

    fun populateData(movieData : MovieData){
        this.movieData = movieData
        Glide.with(view.context).load("${movieData.movieSmallPosterUrl}${movieData.posterImagePath}").into(moviePoster)
        movieTitle.text = movieData.title
        movieReleaseDate.text = movieData.releaseDate
    }

}