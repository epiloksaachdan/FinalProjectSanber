package com.lazday.news.retrofit

import com.lazday.news.room.BookmarkDao
import com.lazday.news.room.BookmarkModel
import org.koin.dsl.module

val repositoryModule = module {
    factory { NewsRepository(get(), get()) }
}

class NewsRepository(
    private val api: NewsApi,
    val bookmark: BookmarkDao,
) {
    suspend fun topHeadlines() = api.topHeadlines()
    suspend fun add(article: NewsModel.Article) {
        bookmark.add(
            BookmarkModel(
                publishedAt = article.publishedAt,
                urlImage = article.urlToImage,
                title = article.title,
            )
        )
    }
}