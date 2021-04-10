package com.lazday.news.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NewsSaveModel::class],
    version = 1,
    exportSchema = false
)

abstract class NewsDatabase : RoomDatabase() {
    abstract val newsSaveDao: NewsSaveDao
}