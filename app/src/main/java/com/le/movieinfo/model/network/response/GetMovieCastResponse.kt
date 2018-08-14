package com.le.movieinfo.model.network.response

import com.google.gson.annotations.SerializedName

data class GetMovieCastResponse(@field:SerializedName("id") val movieId: Int, @field:SerializedName("cast") val castList: List<MovieCastInfo>)