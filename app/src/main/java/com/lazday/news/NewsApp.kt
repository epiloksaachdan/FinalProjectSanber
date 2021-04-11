package com.lazday.news

import android.app.Application
import com.lazday.news.ui.news.newsModule
import com.lazday.news.ui.news.newsViewModel
import com.lazday.news.retrofit.networkModule
import com.lazday.news.retrofit.repositoryModule
import com.lazday.news.room.databaseModule
import com.lazday.news.ui.home.homeModule
import com.lazday.news.ui.home.homeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApp)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                homeModule,
                homeViewModel,
                newsModule,
                newsViewModel
            )
        }
    }
}