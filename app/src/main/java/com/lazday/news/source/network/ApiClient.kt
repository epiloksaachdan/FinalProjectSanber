package com.lazday.news.source.network

import retrofit2.http.GET

interface ApiClient {

    @GET("top-headlines?country=us&apiKey=a3ef8c84c1c7441ca3892e18d4f00a47")
    suspend fun topHeadlines() : NewsModel

}