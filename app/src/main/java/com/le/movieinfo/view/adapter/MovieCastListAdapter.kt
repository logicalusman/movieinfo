package com.le.movieinfo.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.le.movieinfo.R
import com.le.movieinfo.domain.MovieCastData
import com.le.movieinfo.view.viewholder.MovieCastViewHolder

class MovieCastListAdapter(val context: Context) : RecyclerView.Adapter<MovieCastViewHolder>() {
    var movieCastList: List<MovieCastData>? = null
        set(list) {
            field = list
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {
        return MovieCastViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_cast_list_cell, parent, false))
    }

    override fun getItemCount(): Int {
        movieCastList?.let {
            return it.size
        }

        return 0
    }

    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        movieCastList?.get(position)?.let { holder.populateData(it) }
    }

}