package com.lazday.news.source.news

import com.lazday.news.source.network.ArticleModel
import com.lazday.news.source.network.ApiClient
import org.koin.dsl.module

val repositoryModule = module {
    factory { NewsRepository(get(), get()) }
}

class NewsRepository(
    private val api: ApiClient,
    val db: NewsDao,
) {
    suspend fun fetchNews() = api.topHeadlines()
    suspend fun bookmark(articleModel: ArticleModel) {
        when (articleModel.bookmark) {
            0 -> articleModel.bookmark = 1
            else -> articleModel.bookmark = 0
        }
        db.bookmark( articleModel )
    }
}