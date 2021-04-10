package com.lazday.news.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tableSave")
data class NewsSaveModel (
    @PrimaryKey(autoGenerate = false)
    val publishedAt: String,
    val urlImage: String,
    val title: String,
)