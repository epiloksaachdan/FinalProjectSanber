package com.lazday.news

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.lazday.news.source.repositoryModule
import com.lazday.news.source.network.networkModule
import com.lazday.news.source.room.databaseModule
import com.lazday.news.ui.bookmark.bookmarkModule
import com.lazday.news.ui.bookmark.bookmarkViewModel
import com.lazday.news.ui.home.homeModule
import com.lazday.news.ui.home.homeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin {
            androidLogger()
            androidContext(this@NewsApp)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                homeModule,
                homeViewModel,
                bookmarkModule,
                bookmarkViewModel
            )
        }
    }
}