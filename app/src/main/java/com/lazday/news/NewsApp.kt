package com.lazday.news

import android.app.Application
import com.lazday.news.home.pageModule
import com.lazday.news.home.viewModelModule
import com.lazday.news.retrofit.networkModule
import com.lazday.news.retrofit.repositoryModule
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
                listOf(
                    networkModule, repositoryModule, pageModule, viewModelModule
                )
            )
        }
    }
}