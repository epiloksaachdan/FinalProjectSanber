package com.lazday.news.retrofit

import com.lazday.news.room.BookmarkDao
import com.lazday.news.room.BookmarkModel
import org.koin.dsl.module

val repositoryModule = module {
    factory { NewsRepository(get(), get()) }
}

class NewsRepository(
    private val api: NewsApi,
    private val bookmark: BookmarkDao,
) {
    suspend fun topHeadlines() = api.topHeadlines()
    suspend fun add(bookmarkModel: BookmarkModel) = bookmark.add(bookmarkModel)
    fun bookmarks() = bookmark.findAll()
}