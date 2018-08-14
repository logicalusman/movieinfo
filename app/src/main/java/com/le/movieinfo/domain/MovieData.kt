package com.le.movieinfo.domain

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

/**
 * Domain object - can be passed freely across layers.
 */
data class MovieData(val id: Int,
                     val title: String,
                     val overview: String,
                     val voteCount: Int,
                     val level: Double,
                     val posterImagePath: String,
                     val backdropImagePath: String,
                     val voteAverage: Double,
                     val releaseDate: String,
                     val languageCode: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString()) {
    }

    val movieSmallPosterUrl = "https://image.tmdb.org/t/p/w92"
    val movieLargePosterUrl = "https://image.tmdb.org/t/p/w300"

    fun releaseDate(): Date {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(releaseDate)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeInt(voteCount)
        parcel.writeDouble(level)
        parcel.writeString(posterImagePath)
        parcel.writeString(backdropImagePath)
        parcel.writeDouble(voteAverage)
        parcel.writeString(releaseDate)
        parcel.writeString(languageCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieData> {

        override fun createFromParcel(parcel: Parcel): MovieData {
            return MovieData(parcel)
        }

        override fun newArray(size: Int): Array<MovieData?> {
            return arrayOfNulls(size)
        }
    }
}