package com.le.movieinfo.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.le.movieinfo.R
import com.le.movieinfo.domain.MovieData
import com.le.movieinfo.view.fragment.MovieDetailsFragment

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE_DATA = "com.le.movieinfo.extra_movie_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        val movieData = intent.getParcelableExtra<MovieData>(EXTRA_MOVIE_DATA)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.movieDetailsContainer, MovieDetailsFragment.newInstance(movieData)).commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
