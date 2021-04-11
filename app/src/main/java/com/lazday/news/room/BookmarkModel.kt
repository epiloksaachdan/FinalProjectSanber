package com.lazday.news.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tableBookmark")
data class BookmarkModel (
    @PrimaryKey(autoGenerate = false)
    val publishedAt: String,
    val urlImage: String,
    val title: String,
)