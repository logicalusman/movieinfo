package com.le.movieinfo.di

import com.le.movieinfo.model.MovieRepository
import com.le.movieinfo.model.network.TheMovieDbApi
import com.le.movieinfo.model.network.NetworkRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesTheMovieDbApi(): TheMovieDbApi {
        return TheMovieDbApi.createTheMovieDbApi()
    }

    @Provides
    @Singleton
    fun providesNetworkRepository(theMovieDbApi: TheMovieDbApi): NetworkRepository {
        return NetworkRepository(theMovieDbApi)
    }

    @Provides
    @Singleton
    fun providesMoviesRepository(networkRepository: NetworkRepository): MovieRepository {
        return MovieRepository(networkRepository)
    }

}