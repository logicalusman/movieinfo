package com.le.movieinfo.model.network.response

import com.google.gson.annotations.SerializedName

data class MovieCastInfo(val name: String, @field:SerializedName("profile_path") val profilePicturePath: String?)