package com.lazday.news.source.network

import com.lazday.news.source.news.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("top-headlines?country=id&apiKey=a3ef8c84c1c7441ca3892e18d4f00a47")
    suspend fun fetchAll() : NewsModel

    @GET("top-headlines")
    suspend fun fetchPage(
            @Query("apiKey") apiKey: String,
            @Query("country") country: String,
            @Query("category") category: String,
            @Query("q") query: String,
            @Query("page") page: Int /// max page from totalSize = 20 /page
    ) : NewsModel

    @GET("top-headlines")
    suspend fun fetchSearch(
            @Query("apiKey") apiKey: String,
            @Query("country") country: String,
            @Query("category") category: String,
            @Query("q") query: String,
    ) : NewsModel

}