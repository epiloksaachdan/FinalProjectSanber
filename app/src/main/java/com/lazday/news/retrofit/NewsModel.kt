package com.lazday.news.retrofit

data class NewsModel (val articles: List<Article>) {
    data class Article (val author: String, val title: String)
}