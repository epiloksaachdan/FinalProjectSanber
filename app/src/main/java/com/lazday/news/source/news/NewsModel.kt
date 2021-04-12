package com.lazday.news.source.news

import androidx.room.Entity
import androidx.room.PrimaryKey

data class NewsModel (
    val articles: List<ArticleModel>
)

@Entity (tableName = "tableArticle")
data class ArticleModel (
        val source: SourceModel?,
        val author: String? = "",
        val title: String? = "",
        val description: String? = "",
        val url: String? = "",
        val urlToImage: String? = "",
        @PrimaryKey(autoGenerate = false)
        val publishedAt: String,
        val content: String? = "",
        var bookmark: Int = 0,
)

data class SourceModel (
        val id: String? = "",
        val name: String? = ""
)