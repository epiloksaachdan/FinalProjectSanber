package com.lazday.news.retrofit

import retrofit2.http.GET

interface NewsApi {

    @GET("top-headlines?country=us&apiKey=a3ef8c84c1c7441ca3892e18d4f00a47")
    suspend fun topHeadlines() : NewsModel

}