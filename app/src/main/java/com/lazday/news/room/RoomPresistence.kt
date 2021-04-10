package com.lazday.news.room

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideNewsSaveDao(get()) }
}

fun provideDatabase(application: Application): NewsDatabase {
    return Room.databaseBuilder(application, NewsDatabase::class.java, "lazdayNews.db")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideNewsSaveDao(database: NewsDatabase): NewsSaveDao {
    return  database.newsSaveDao
}

