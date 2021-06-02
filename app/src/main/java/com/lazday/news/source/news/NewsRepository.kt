package com.lazday.news.source.news

import com.lazday.news.source.network.ApiClient
import org.koin.dsl.module
import timber.log.Timber
import kotlin.math.ceil

private const val apiKey = "a3ef8c84c1c7441ca3892e18d4f00a47"
private const val country = "id"

val repositoryModule = module {
    factory { NewsRepository(get(), get()) }
}

class NewsRepository(
    private val api: ApiClient,
    val db: NewsDao,
) {
    suspend fun fetch() = api.fetchAll()

    suspend fun search(
            category: String? = "",
            query: String
    ): NewsModel {
        return api.fetchSearch(apiKey, country, category!!, query)
    }

    suspend fun page(
        category: String? = "",
        query: String,
        page: Int
    ): NewsModel {
        return api.fetchPage(apiKey, country, category!!, query, page)
    }

    suspend fun find(articleModel: ArticleModel) = db.find(articleModel.publishedAt)

    suspend fun save(articleModel: ArticleModel) {
        db.save( articleModel )
    }

    suspend fun remove(articleModel: ArticleModel) {
        db.remove( articleModel )
    }

}