package com.le.movieinfo.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.le.movieinfo.R
import com.le.movieinfo.view.fragment.MovieListFragment

class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.homeContainer, MovieListFragment.newInstance()).commit()
        }

    }
}
