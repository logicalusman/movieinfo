package com.le.movieinfo.model.network.response

import com.google.gson.annotations.SerializedName

data class GetUpcomingMoviesResponse (@field:SerializedName("results") val moviesList: List<MovieInfo>)