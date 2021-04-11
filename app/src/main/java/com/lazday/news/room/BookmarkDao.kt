package com.lazday.news.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM tableBookmark")
    fun findAll(): LiveData<List<BookmarkModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(bookmarkModel: BookmarkModel)
}