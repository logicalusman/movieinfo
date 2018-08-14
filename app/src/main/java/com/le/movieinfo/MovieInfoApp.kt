package com.le.movieinfo

import android.app.Application
import com.le.movieinfo.di.AppModule
import com.le.movieinfo.di.DaggerDataComponent
import com.le.movieinfo.di.DataComponent
import com.le.movieinfo.di.DataModule

class MovieInfoApp : Application() {

    companion object {
        lateinit var dataComponent: DataComponent
    }

    override fun onCreate() {
        super.onCreate()
        dataComponent = DaggerDataComponent.builder().appModule(AppModule(this)).dataModule(DataModule()).build()
    }

}