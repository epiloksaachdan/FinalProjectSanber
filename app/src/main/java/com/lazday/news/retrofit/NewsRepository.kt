package com.lazday.news.retrofit

import org.koin.dsl.module

val repositoryModule = module {
    factory { NewsRepository(get()) }
}

class NewsRepository(private val newsApi: NewsApi) {
    suspend fun topHeadlines() = newsApi.topHeadlines()
}