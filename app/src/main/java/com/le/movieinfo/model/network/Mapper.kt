package com.le.movieinfo.model.network

import com.le.movieinfo.domain.MovieCastData
import com.le.movieinfo.domain.MovieData
import com.le.movieinfo.model.network.response.MovieCastInfo
import com.le.movieinfo.model.network.response.MovieInfo

/**
 * Converts Retrofit response objects into pojo, so they can be passed across layers.
 */
object Mapper {

    fun toMovieData(movieInfoList: List<MovieInfo>): List<MovieData> {
        val movieDataList = ArrayList<MovieData>(movieInfoList.size)
        movieInfoList.forEachIndexed { _, value ->
            movieDataList.add(MovieData(value.id, value.title, value.overview, value.voteCount, value.level, value.posterImagePath, value.backdropImagePath, value.voteAverage, value.releaseDate, value.languageCode))

        }
        return movieDataList
    }

    fun toMovieCastData(movieCastInfoList: List<MovieCastInfo>): List<MovieCastData> {
        val movieCastDataList = ArrayList<MovieCastData>(movieCastInfoList.size)
        movieCastInfoList.forEachIndexed { _, value ->
            movieCastDataList.add(MovieCastData(value.name, value.profilePicturePath))
        }
        return movieCastDataList
    }

}