package com.le.movieinfo.di

import com.le.movieinfo.model.MovieRepository
import com.le.movieinfo.view.fragment.MovieDetailsFragment
import com.le.movieinfo.view.fragment.MovieListFragment
import com.le.movieinfo.viewmodel.MovieDetailsViewModel
import com.le.movieinfo.viewmodel.MovieListViewModel
import com.le.movieinfo.model.network.NetworkRepository

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, ViewModelModule::class))
interface DataComponent {
    fun inject(movieListFragment: MovieListFragment)
    fun inject(movieListViewModel: MovieListViewModel)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
    fun inject(movieDetailsViewModel: MovieDetailsViewModel)
    fun inject(networkRepository: NetworkRepository)
    fun inject(movieRepository: MovieRepository)
}