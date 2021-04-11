package com.lazday.news.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BookmarkModel::class],
    version = 1,
    exportSchema = false
)

abstract class NewsDatabase : RoomDatabase() {
    abstract val bookmarkDao: BookmarkDao
}