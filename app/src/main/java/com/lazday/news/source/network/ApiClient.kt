package com.lazday.news.source.network

import com.lazday.news.source.news.NewsModel
import retrofit2.http.GET

interface ApiClient {

    @GET("top-headlines?country=id&apiKey=a3ef8c84c1c7441ca3892e18d4f00a47")
    suspend fun topHeadlines() : NewsModel

}