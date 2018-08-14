package com.le.movieinfo.domain

/**
 * Domain object - can be passed freely across layers.
 */
data class MovieCastData(val name: String, val profilePicturePath: String?) {

    val castProfilePictureUrl = "https://image.tmdb.org/t/p/w45"

}