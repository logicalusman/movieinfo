package com.le.movieinfo.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.le.movieinfo.R
import com.le.movieinfo.domain.MovieData
import com.le.movieinfo.view.viewholder.MovieListViewHolder
import rx.subjects.PublishSubject

class MovieListAdapter(private val context: Context) : RecyclerView.Adapter<MovieListViewHolder>() {

    var moviesList: List<MovieData>? = null
        set(list) {
            field = list
            notifyDataSetChanged()
        }
    val rowClickSubject = PublishSubject.create<MovieData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_list_cell, parent, false),rowClickSubject)
    }

    override fun getItemCount(): Int {
        moviesList?.let {
            return it.size
        }

        return 0
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        moviesList?.get(position)?.let { holder.populateData(it) }
    }


}