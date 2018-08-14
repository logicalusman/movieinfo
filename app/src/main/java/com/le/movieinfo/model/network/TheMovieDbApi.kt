package com.le.movieinfo.model.network

import com.le.movieinfo.model.network.response.GetMovieCastResponse
import com.le.movieinfo.model.network.response.GetUpcomingMoviesResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {

    companion object {
        val BASE_URL = "https://api.themoviedb.org/"
        val API_KEY = "45665d6bcd780e660524722afce9cda5"

        fun createTheMovieDbApi(): TheMovieDbApi {
            val httpBuilder = OkHttpClient.Builder()
            httpBuilder.addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(httpBuilder.build())
                    .build()

            return retrofit.create(TheMovieDbApi::class.java)
        }
    }

    @GET("3/movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") apiKey: String): Observable<GetUpcomingMoviesResponse>

    @GET("3/movie/{movieId}/credits")
    fun getMovieCast(@Path("movieId") movieId: Int, @Query("api_key") apiKey: String): Observable<GetMovieCastResponse>

}