package com.le.movieinfo.model.network.response

import com.google.gson.annotations.SerializedName

data class MovieInfo(val id: Int,
                     val title: String,
                     val overview : String,
                     @field:SerializedName("vote_count") val voteCount: Int,
                     @field:SerializedName("level") val level: Double,
                     @field:SerializedName("poster_path") val posterImagePath: String,
                     @field:SerializedName("backdrop_path") val backdropImagePath: String,
                     @field:SerializedName("vote_average") val voteAverage: Double,
                     @field:SerializedName("release_date") val releaseDate: String,
                     @field:SerializedName("original_language") val languageCode: String
)