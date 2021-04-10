package com.lazday.news.retrofit

import com.lazday.news.room.NewsSaveDao
import com.lazday.news.room.NewsSaveModel
import org.koin.dsl.module

val repositoryModule = module {
    factory { NewsRepository(get(), get()) }
}

class NewsRepository(
    private val api: NewsApi,
    private val bookmark: NewsSaveDao,
) {
    suspend fun topHeadlines() = api.topHeadlines()
    suspend fun add(newsSaveModel: NewsSaveModel) = bookmark.add(newsSave = newsSaveModel)
    fun bookmarks() = bookmark.findAll()
}