package com.le.movieinfo.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.le.movieinfo.domain.MovieCastData
import kotlinx.android.synthetic.main.movie_cast_list_cell.view.*

class MovieCastViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val movieCastProfilePictureIv = view.movie_cast_profile_picture_iv
    val movieCastNameTv = view.movie_cast_name_tv

    fun populateData(movieCastData: MovieCastData) {
        movieCastData.profilePicturePath?.let {
            Glide.with(view.context).load("${movieCastData.castProfilePictureUrl}${it}").into(movieCastProfilePictureIv)
        }
        movieCastNameTv.text = movieCastData.name
    }


}